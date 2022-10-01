var LowStockItem = function () {

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

    var dtStockItem = function () {
        var table_name = "tbl_stock_home_list";
        var oTable = $('#' + table_name).dataTable({
        	responsive: true,
            "processing": true,
            "serverSide": true,
            "ajax": $.fn.dataTable.pipeline({
                url: "restapi/dashboard/lowstockitem",
                pages: 5
            }),
            columns: [
                {
                    orderable: false,
                    searchable: false,
                    width: "2%",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    },
                    responsivePriority: 1
                },

                {	
					data: 'partCode',
    				responsivePriority: 2
    			},
                {	
					data: 'partName',
                	responsivePriority: 1
                },
                {
                	data: 'batchNo',
                    responsivePriority: 2
                },
                {
                	data: 'qtyOnHand',
                	responsivePriority: 2
                },
                {
                	data: 'minQty',
                	responsivePriority: 2
                }

            ],
            aoColumnDefs: [{
                width: "2%",
                searchable: false,
                targets: 6,
                data: "id",
                render: function (data, type, row, meta) {
                    return ButtonUtil.getCommonBtnView("StockHome.viewStock", data);
                    var html = "<div align='center'>" +
                        "<div class='btn-group'>" +
                        "<a class='btn btn-xs btn-blue dropdown-toggle btn-sm' data-toggle='dropdown' href='#'>" +
                        "<i class='fa fa-cog'></i> <span class='caret'></span>" +
                        "</a>" +
                        "<ul role='menu' class='dropdown-menu pull-right'>" +
                        "<li role='presentation'><a href='../stock/item?stockId=" + data + "' type='button' role='menuitem' tabindex='-1'><i class='fa fa-edit'></i> View Batch </a></li>" +
                        "</ul>" +
                        "</div>" +
                        "</div>";
                    return html;
                    // return "<button type='button' class='btn btn-block'>SSSSSSSSS</button>";
                    // return ButtonUtil.getHomeBtnWithURL("aodReturn", data);
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
            aLengthMenu: [[5, 10, 15, 20],
                [5, 10, 15, 20]],
            iDisplayLength: 5,
            sPaginationType: "full_numbers",
            sPaging: 'pagination'

        });
        wapperSet(table_name)
    };

    var wapperSet = function (table_name) {
        $('#' + table_name + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + table_name + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + table_name + '_wrapper .dataTables_length select').select2();
        $('#' + table_name + '_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
    }

    var viewStock = function (id) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stock/ledger-modal-view';
            $modal.load(url, '', function () {
                StockLedgerHome.initDataTable(id);
                $modal.modal();
            });
        }, 1000);
    }
    return {
        initDataTable: function () {
            dtStockItem();
        },
        viewStock: function (id) {
            viewStock(id)
        }


    };

}();