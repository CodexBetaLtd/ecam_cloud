var InventoryDetailTable = function () {

    //
    // Pipelining function for DataTables. To be used to the `ajax` option of DataTables
    //
    $.fn.dataTable.pipeline = function (opts) {
        // Configuration options
        var conf = $.extend({
            pages: 5,     // number of pages to cache
            url: '',      // script url
            data: null,   // function or object with parameters to send to the server
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
            }
            else if (cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper) {
                // outside cached data - need to make a request
                ajax = true;
            }
            else if (JSON.stringify(request.order) !== JSON.stringify(cacheLastRequest.order) ||
                JSON.stringify(request.columns) !== JSON.stringify(cacheLastRequest.columns) ||
                JSON.stringify(request.search) !== JSON.stringify(cacheLastRequest.search)
            ) {
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
                    // As a function it is executed with the data object as an arg
                    // for manipulation. If an object is returned, it is used as the
                    // data object to submit
                    var d = conf.data(request);
                    if (d) {
                        $.extend(request, d);
                    }
                }
                else if ($.isPlainObject(conf.data)) {
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
            }
            else {
                json = $.extend(true, {}, cacheLastJson);
                json.draw = request.draw; // Update the echo for each response
                json.data.splice(0, requestStart - cacheLower);
                json.data.splice(requestLength, json.data.length);

                drawCallback(json);
            }
        }
    };

    // Register an API method that will empty the pipelined data, forcing an Ajax
    // fetch on the next draw (i.e. `table.clearPipeline().draw()`)
    $.fn.dataTable.Api.register('clearPipeline()', function () {
        return this.iterator('table', function (settings) {
            settings.clearCache = true;
        });
    });

    var initDataTable = function () {
    	
        var tableName = "report_tbl_inventory";
        
        $('#' + tableName).dataTable().fnDestroy();
        
        var oTable = $('#' + tableName).dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../../restapi/report/inventory/searchDetail?" + $('#frmReportInventory').serialize(),
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false,
                width: "2%",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            } ,
            {   orderable: false,
                searchable: false,
                data: 'partCode'
            },
            {
            	data: 'partDescription',
            	orderable: false,
                searchable: false,
            },
            {
            	data: 'remainQty',
            	orderable: false,
            	searchable: false,
            	render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {
            	data: 'avgCost',
            	orderable: false,
            	searchable: false,
            	render: $.fn.dataTable.render.number(',', '.', 2, '')},
            {
            	data: 'itemTotal',
            	orderable: false,
                searchable: false,
            	render: $.fn.dataTable.render.number(',', '.', 2, '') 
            	},
            ],
            oLanguage: {
                sLengthMenu: "Show _MENU_ Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [
                [1, 'asc']
            ],
            footerCallback: function ( row, data, start, end, display ) {
                var api = this.api(), data;
      
                // Remove the formatting to get integer data for summation
                var intVal = function ( i ) {
                    return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '')*1 :
                        typeof i === 'number' ?
                            i : 0;
                };
      
                // Total over all pages
                data = api.column( 4 ).cache('order');
                total = data.length ?
                    data.reduce( function (a, b) {
                            return intVal(a) + intVal(b);
                    } ) :
                    0;
      
                // Update footer
                $( api.column( 4 ).footer() ).html(
                    '$'+ total
                );
            },
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination'
        });
        $('#' + tableName + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableName + '_wrapper .dataTables_length select').select2();
        $('#' + tableName + '_wrapper .dataTables_filter').hide();
    };

    var toDate = function (longDate) {
        if (longDate != null) {
            return moment(longDate).format("DD-MM-YYYY");
        }
        return "";
    };

    return {
        initDataTable: function () {
            initDataTable();
        }
    };
}();