var PartStockAgeDetailTable = function () {

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

    var initTableByDays = function () {
    	
        var tableName = "tbl_part_stock_age_by_days";
        
        $('#' + tableName).dataTable().fnDestroy();
        
        var oTable = $('#' + tableName).dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../../restapi/report/partstockage/datatable?" + $('#frmReportStockAge').serialize(),
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false, 
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            }, {
                data: 'stockAgePartCode',
                searchable: false,
            }, {
                data: 'stockAgePartDescription',
                searchable: false,
                orderable: false,
            },{
            	data: 'stockAgeLastGRN',
                searchable: false,
                orderable: false,
            },{
            	data: 'stockAgeLastGRNDate',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
                	return toDate(data);
                },
            },{
            	data: 'stockAgeLastAOD',
                searchable: false,
                orderable: false,
            },{
            	data: 'stockAgeLastAODDate',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
                	return toDate(data);
                },
            },{
            	data: 'stockAgePartUnitPrice',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgePartUnitPrice!= null) {                
	                	return  row.stockAgePartUnitPrice.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data: 'stockAgeRemainQty',
                searchable: false,
                orderable: false, 
            },{
            	data: 'stockAgeQtyOfBelow90Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOfBelow90Days!= null) {                
	                	return  row.stockAgeQtyOfBelow90Days;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            }, {
                data: 'stockAgeValueOfBelow90Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfBelow90Days!= null) {                
	                	return  row.stockAgeValueOfBelow90Days.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data: 'stockAgeQtyOfBetween91And180Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfBetween91And180Days!= null) {                
	                	return  row.stockAgeQtyOfBetween91And180Days;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeValueOfBetween91And180Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfBetween91And180Days != null) {                
	                	return  row.stockAgeValueOfBetween91And180Days.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeQtyOfBetween181And360Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOfBetween181And360Days != null) {                
	                	return  row.stockAgeQtyOfBetween181And360Days;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeValueOfBetween181And360Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfBetween181And360Days != null) {                
	                	return  row.stockAgeValueOfBetween181And360Days.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeQtyOfAbouve361Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfAbouve361Days != null) {                
	                	return  row.stockAgeQtyOfAbouve361Days;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeValueOfAbouve361Days',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfAbouve361Days != null) {                
	                	return  row.stockAgeValueOfAbouve361Days.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                },
                className: "currency"
            } ],
            oLanguage: {
                sLengthMenu: "Show _MENU_ Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [
                [2, 'asc']
            ],
            // set the initial value
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            footerCallback: function (row, data, start, end, display) {
                var api = this.api(), data, 
                 intVal = function ( i ) {
                    return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '')*1 :
                        typeof i === 'number' ?
                            i : 0;
                }, 
                
                col8 = api.column(8, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col9 = api.column(9, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col10 = api.column(10, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0);
                col11 = api.column(11, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col12 = api.column(12, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col13 = api.column(13, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col14 = api.column(14, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col15 = api.column(15, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0);
                col16 = api.column(16, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                
                $(api.column(8).footer()).html(col8.toFixed(1));
                $(api.column(9).footer()).html(col9.toFixed(1));
                $(api.column(10).footer()).html(col10.toFixed(2));
                $(api.column(11).footer()).html(col11.toFixed(1));
                $(api.column(12).footer()).html(col12.toFixed(2));
                $(api.column(13).footer()).html(col13.toFixed(1));
                $(api.column(14).footer()).html(col14.toFixed(2));
                $(api.column(15).footer()).html(col15.toFixed(1));
                $(api.column(16).footer()).html(col16.toFixed(2));
            }
        });
        $('#' + tableName + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableName + '_wrapper .dataTables_length select').select2();
        $('#' + tableName + '_wrapper .dataTables_filter').hide();
    };
    
    var initTableByYears = function () {
    	
        var tableName = "tbl_part_stock_age_by_year";
        
        $('#' + tableName).dataTable().fnDestroy();
        
        var oTable = $('#' + tableName).dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../../restapi/report/partstockage/datatable?" + $('#frmReportStockAge').serialize(),
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false, 
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            }, {
                data: 'stockAgePartCode',
                searchable: false,
            }, {
                data: 'stockAgePartDescription',
                searchable: false,
                orderable: false,
            },{
            	data: 'stockAgeLastGRN',
                searchable: false,
                orderable: false,
            },{
            	data: 'stockAgeLastGRNDate',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
                	return toDate(data);
                },
            },{
            	data: 'stockAgeLastAOD',
                searchable: false,
                orderable: false,
            },{
            	data: 'stockAgeLastAODDate',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
                	return toDate(data);
                },
            },{
            	data: 'stockAgePartUnitPrice',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgePartUnitPrice!= null) {                
	                	return  row.stockAgePartUnitPrice.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data: 'stockAgeRemainQty',
                searchable: false,
                orderable: false, 
            }, {
                data: 'stockAgeQtyOfLessThan1Year',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOfLessThan1Year!= null) {                
	                	return  row.stockAgeQtyOfLessThan1Year;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            }, {
                data: 'stockAgeValueOfLessThan1Year',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfLessThan1Year!= null) {                
	                	return  row.stockAgeValueOfLessThan1Year.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeQtyOf1Year',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOf1Year != null) {                
	                	return  row.stockAgeQtyOf1Year;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            }, {
            	data:'stockAgeValueOf1Year',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOf1Year != null) {                
	                	return  row.stockAgeValueOf1Year.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeQtyOf2Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOf2Years != null) {                
	                	return  row.stockAgeQtyOf2Years;
	                } else {
	                	return "0.0";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeValueOf2Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOf2Years != null) {                
	                	return  row.stockAgeValueOf2Years.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                }, 
                className: "currency"
            },{
            	data:'stockAgeQtyOf3Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOf3Years != null) {                
	                	return  row.stockAgeQtyOf3Years;
	                } else {
	                	return "0.0";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeValueOf3Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOf3Years != null) {                
	                	return  row.stockAgeValueOf3Years.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeQtyOf4Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOf4Years != null) {                
	                	return  row.stockAgeQtyOf4Years;
	                } else {
	                	return "0.0";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeValueOf4Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOf4Years != null) {                
	                	return  row.stockAgeValueOf4Years.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeQtyOf5To10Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOf5To10Years != null) {                
	                	return  row.stockAgeQtyOf5To10Years;
	                } else {
	                	return "0.0";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeValueOf5To10Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOf5To10Years != null) {                
	                	return  row.stockAgeValueOf5To10Years.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeQtyOfMoreThan10Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeQtyOfMoreThan10Years != null) {                
	                	return  row.stockAgeQtyOfMoreThan10Years;
	                } else {
	                	return "0.0";
	                }
                },
                className: "currency"
            },{
            	data:'stockAgeValueOfMoreThan10Years',
                searchable: false,
                orderable: false,
                render: function (data, type, row, meta) { 
	                if (row.stockAgeValueOfMoreThan10Years != null) {                
	                	return  row.stockAgeValueOfMoreThan10Years.toFixed(2);
	                } else {
	                	return "0.00";
	                }
                },
                className: "currency"
            }],
            oLanguage: {
                sLengthMenu: "Show _MENU_ Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [
                [2, 'asc']
            ],
            // set the initial value
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            footerCallback: function (row, data, start, end, display) {
                var api = this.api(), data, 
                 intVal = function ( i ) {
                    return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '')*1 :
                        typeof i === 'number' ?
                            i : 0;
                }, 
                
                col8 = api.column(8, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col9 = api.column(9, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col10 = api.column(10, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col11 = api.column(11, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col12 = api.column(12, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col13 = api.column(13, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col14 = api.column(14, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col15 = api.column(15, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col16 = api.column(16, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col17 = api.column(17, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col18 = api.column(18, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col19 = api.column(19, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col20 = api.column(20, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col21 = api.column(21, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
                col22 = api.column(22, { page: 'all' }).data().reduce(function (a, b) { return a + b; }, 0); 
          
                $(api.column(8).footer()).html(col8.toFixed(1));
                $(api.column(9).footer()).html(col9.toFixed(1));
                $(api.column(10).footer()).html(col10.toFixed(2));
                $(api.column(11).footer()).html(col11.toFixed(1));
                $(api.column(12).footer()).html(col12.toFixed(2));
                $(api.column(13).footer()).html(col13.toFixed(1));
                $(api.column(14).footer()).html(col14.toFixed(2));
                $(api.column(15).footer()).html(col15.toFixed(1));
                $(api.column(16).footer()).html(col16.toFixed(2));
                $(api.column(17).footer()).html(col17.toFixed(1));
                $(api.column(18).footer()).html(col18.toFixed(2));
                $(api.column(19).footer()).html(col19.toFixed(1));
                $(api.column(20).footer()).html(col20.toFixed(2));
                $(api.column(21).footer()).html(col21.toFixed(1));
                $(api.column(22).footer()).html(col22.toFixed(2));
            }
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
    	
    	initTableByDays: function () {
    		initTableByDays();
        },
        
        initTableByYears: function() {
        	initTableByYears();
		}
    };
}();