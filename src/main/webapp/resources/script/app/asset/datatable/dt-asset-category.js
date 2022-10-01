var AssetCategoryDataTable = function () {

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
                    requestStart = requestStart - (requestLength * (conf.pages - 1));

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


    var runDataTable = function () {
    	$('#assetCategoryTbl').dataTable().fnDestroy();
    
        var oTable = $('#assetCategoryTbl').DataTable({
        	
        	responsive: true,
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
            	url: "../../restapi/assetCategory/tabledatabytype?type=" + categoryType,
                pages: 5
            }),
            columns: [{            	
                orderable: false,
                searchable: false,
                width: "8%",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                },
                responsivePriority: 1,
            },
                {
            		data: 'name',
            		responsivePriority: 1,
            	 },
                {	
            		data: 'description',
            		responsivePriority: 2,
            	},
                {	
            		data: 'parentName',
            		responsivePriority: 2,
            	}
            ],
            aoColumnDefs: [],
            oLanguage: {
                sLengthMenu: "Show_MENU_Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [
                [1, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            dom : "<'row'<'col-sm-8 col-xs-8 dtblassetcategory'><'col-sm-4 col-xs-4'f>>" + "<'row '<'col-sm-12 col-xs-12 'tr>>" + "<'row panel-body'<'col-sm-4'i><'col-sm-8'p>>",
			initComplete : function() {
				$("div.dtblassetcategory").html( "<button class='btn btn-blue btn-sm active tooltips' data-toggle='modal' type='button' id='btnAssetCategoryNew' onclick='AssetAdd.assetCategoryAddView();' ><i class='clip-plus-circle-2  btn-new'></i> New</button>");
			},
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: "AssetAdd.setAssetCategory",
                aoData:[  
                    {
                        sName : "id",
                    }, {
                        sName : "name"
                    },
                ],
            },
        });

        $('#assetCategoryTbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#assetCategoryTbl_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#assetCategoryTbl_wrapper .dataTables_length select').select2();
        $('#assetCategoryTbl_wolumn_toggler input[type="checkbox"]').change(function () {

            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });

    };
    
    return {
    	runDataTable: function () {
            runDataTable();
        }
    };
}();