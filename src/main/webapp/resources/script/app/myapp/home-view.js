var MyApp = function () {

    var installApp = function (appId) {
        $.ajax({
            type: "GET",
            url: '../myapp/install?appId=' + appId,
            dataType: 'json',
            success: function (val) {
            	$('#common-modal').modal("toggle");
                if (val) {
                	initAppDataTable();
                } else {
                    alert("Error Occured while Subscribing. Try Again.");
                }                
            }
        });
    };

    var uninstallApp = function (appId) {
        $.ajax({
            type: "GET",
            url: '../myapp/uninstall?appId=' + appId,
            dataType: 'json',
            success: function (val) {
            	$('#common-modal').modal("toggle");
                if (val) {                	
                	initAppDataTable();
                } else {
                    alert("Error Occured while UnSubscribing. Try Again.");
                }                
            }
        });
    };

    var installConfirmationModal = function (appId) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../myapp/install-confirmation-view?appId=' + appId;
            $modal.load(url, '', function () {
                InstallConfirmationModal.init(appId);
                $modal.modal();
            });
        }, 1000);
    };

    var uninstallConfirmationModal = function (appId) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../myapp/uninstall-confirmation-view?appId=' + appId;
            $modal.load(url, '', function () {
                UninstallConfirmationModal.init(appId);
                $modal.modal();
            });
        }, 1000);
    };

    $.fn.dataTable.pipeline = function (opts) {
        // Configuration options
        var conf = $.extend({
            pages: 5, // number of pages to cache
            url: '', // script url
            data: null, // function or object with parameters to send to the
            // server
            // matching how `ajax.data` works in DataTables
            method: 'GET' // Ajax HTTP method
        }, opts);

        // Private variables for storing the cache
        var cacheLower = -1;
        var cacheUpper = null;
        var cacheLastRequest = null;
        var cacheLastJson = null;

        return function (request, drawCallback, settings) {
            var ajax = false;
            var requestStart = request.start;
            var drawStart = request.start;
            var requestLength = request.length;
            var requestEnd = requestStart + requestLength;

            if (settings.clearCache) {
                // API requested that the cache be cleared
                ajax = true;
                settings.clearCache = false;
            } else if (cacheLower < 0 || requestStart < cacheLower
                || requestEnd > cacheUpper) {
                // outside cached data - need to make a request
                ajax = true;
            } else if (JSON.stringify(request.order) !== JSON
                    .stringify(cacheLastRequest.order)
                || JSON.stringify(request.columns) !== JSON
                    .stringify(cacheLastRequest.columns)
                || JSON.stringify(request.search) !== JSON
                    .stringify(cacheLastRequest.search)) {
                // properties changed (ordering, columns, searching)
                ajax = true;
            }

            // Store the request for checking next time around
            cacheLastRequest = $.extend(true, {}, request);

            if (ajax) {
                // Need data from the server
                if (requestStart < cacheLower) {
                    requestStart = requestStart
                        - (requestLength * (conf.pages - 1));

                    if (requestStart < 0) {
                        requestStart = 0;
                    }
                }

                cacheLower = requestStart;
                cacheUpper = requestStart + (requestLength * conf.pages);

                request.start = requestStart;
                request.length = requestLength * conf.pages;

                // Provide the same `data` options as DataTables.
                if ($.isFunction(conf.data)) {
                    // As a function it is executed with the data object as an
                    // arg
                    // for manipulation. If an object is returned, it is used as
                    // the
                    // data object to submit
                    var d = conf.data(request);
                    if (d) {
                        $.extend(request, d);
                    }
                } else if ($.isPlainObject(conf.data)) {
                    // As an object, the data given extends the default
                    $.extend(request, conf.data);
                }

                settings.jqXHR = $.ajax({
                    "type": conf.method,
                    "url": conf.url,
                    "data": request,
                    "dataType": "json",
                    "cache": false,
                    "success": function (json) {
                        cacheLastJson = $.extend(true, {}, json);

                        if (cacheLower != drawStart) {
                            json.data.splice(0, drawStart - cacheLower);
                        }
                        if (requestLength >= -1) {
                            json.data.splice(requestLength, json.data.length);
                        }

                        drawCallback(json);
                    }
                });
            } else {
                json = $.extend(true, {}, cacheLastJson);
                json.draw = request.draw; // Update the echo for each response
                json.data.splice(0, requestStart - cacheLower);
                json.data.splice(requestLength, json.data.length);

                drawCallback(json);
            }
        }
    };

    $.fn.dataTable.Api.register('clearPipeline()', function () {
        return this.iterator('table', function (settings) {
            settings.clearCache = true;
        });
    });

    var initAppDataTable = function () {
        
    	$('#my_app_tbl').dataTable().fnDestroy();
    	
        var oTable = $('#my_app_tbl').DataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/myapp/tabledata",
                pages: 5
            }),
            columns: [
                {
                    orderable: false,
                    searchable: false,
                    width: "2%",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                }, {
                    data: 'name'
                }, {
                    data: 'rate'
                }
            ],
            aoColumnDefs: [{ 
                searchable: false,
                orderable: false,
                targets: 3,
                data: "id",
                render: function (data, type, full, meta) {
                    return MyApp.getInstallOrUninstallButton(data);
                }
            }],
            oLanguage: {
                sLengthMenu: "Show _MENU_ Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [[1, 'asc']],
            aLengthMenu: [[5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"]],
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination'
        });
        $('#my_app_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        // modify table search input
        $('#my_app_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        // modify table per page dropdown
        $('#my_app_tbl_wrapper .dataTables_length select').select2();
        // initialzie select2 dropdown
        $('#my_app_tbl_column_toggler input[type="checkbox"]').change(function () {
            /* Get the DataTables object again - this is not a recreation, just a get of the object */
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });  
    };

    var getInstallOrUninstallButton = function (appId) {
        var htmlBtn = "";
        $.ajax({
            type: "GET",
            url: '../restapi/myapp/isAppInstalled?appId=' + appId,
            dataType: 'json',
            async: false,
            success: function (val) {
                if (!val) {
                    htmlBtn = "<div align='center'><a id='app_btn_" + appId + "' onclick='MyApp.installConfirmationModal(" + appId + ")' class='btn btn-green btn-sm' type='button' role='menuitem' tabindex='-1' style='width:110px;text-align: left;'><i id='app_btn_icon_" + appId + "' class='clip-download-3'></i> Subscribe </a></div>";
                } else {
                    htmlBtn = "<div align='center'><a id='app_btn_" + appId + "' onclick='MyApp.uninstallConfirmationModal(" + appId + ")' class='btn btn-bricky btn-sm' type='button' role='menuitem' tabindex='-1' style='width:110px;text-align: left;'><i id='app_btn_icon_" + appId + "' class='fa fa-trash'></i> Unsubscribe </a></div>";
                }
            }
        });

        return htmlBtn;
    };

    return {
        initAppDataTable: function () {
            initAppDataTable();
        },

        installConfirmationModal: function (appId) {
            installConfirmationModal(appId);
        },

        uninstallConfirmationModal: function (appId) {
            uninstallConfirmationModal(appId);
        },

        getInstallOrUninstallButton: function (appId) {
            return getInstallOrUninstallButton(appId);
        },

        installApp: function (appId) {
            installApp(appId);
        },

        uninstallApp: function (appId) {
            uninstallApp(appId);
        }

    };

}();

