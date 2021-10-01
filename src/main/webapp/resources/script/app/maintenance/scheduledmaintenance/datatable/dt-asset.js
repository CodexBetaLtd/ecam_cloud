var AssetSelectModal = function () {

    $.fn.dataTable.pipeline = function (opts) {
        var conf = $.extend({
        	pages: 5,
        	url: '',
        	data: null,
        	method: 'GET'
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
                ajax = true;
            }
            cacheLastRequest = $.extend(true, {}, request);
            if (ajax) {
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
                if ($.isFunction(conf.data)) {
                    var d = conf.data(request);
                    if (d) {
                        $.extend(request, d);
                    }
                } else if ($.isPlainObject(conf.data)) {
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

    var initDataTable = function (bizId) {
        
        $('#asset_tbl').dataTable().fnDestroy();
        
        var oTable = $('#asset_tbl').DataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/asset/findNotPartByBusiness?id=" + parseInt(bizId),
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false,
                width: "8%",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            }, {
                data: 'name'
            }, {
                data: 'code'
            }],
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
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: "TabAsset.addAsset",
                aoData:[  
                    {
                        sName : "id",
                    }, {
                        sName : "name"
                    },{
                        sName : "code"
                    }
                ],
            },
        });
        $('#asset_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        // modify table search input
        $('#asset_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        // modify table per page dropdown
        $('#asset_tbl_wrapper .dataTables_length select').select2();
        // initialzie select2 dropdown
    };


    return {

        init: function (bizId) {
            initDataTable(bizId);
        }
    };
}();