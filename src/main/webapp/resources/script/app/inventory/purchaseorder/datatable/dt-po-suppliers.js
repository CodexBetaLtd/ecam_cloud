var DatatableModalSuppliers = function () {
	
	$.fn.dataTable.pipeline = function (opts) {
        // Configuration options
        var conf = $.extend({
            pages: 5, // number of pages to cache
            url: '', // script url
            data: null, // function or object with parameters to send to the
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
                ajax = true;
                settings.clearCache = false;
            } else if (cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper) {
                ajax = true;
            } else if (JSON.stringify(request.order) !== JSON.stringify(cacheLastRequest.order)
                    || JSON.stringify(request.columns) !== JSON.stringify(cacheLastRequest.columns)
                    || JSON.stringify(request.search) !== JSON.stringify(cacheLastRequest.search)) {
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

    var initTable = function (tableId, url, method) {
        
        $('#' + tableId).dataTable().fnDestroy();
      
        var oTable = $('#' + tableId).DataTable({
        	responsive: true,
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: url,
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false, 
                width: "8%",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
            {data: 'name'},
            {data: 'code'},
            {data : 'businessName'}
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
            // set the initial value
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: "DatatableModalSuppliers." + method,
                aoData:[  
                    {
                        sName : "id",
                    }, {
                        sName : "name"
                    }
                ],
            },
        });
        $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableId + '_wrapper .dataTables_length select').select2();
        $('#' + tableId + '_columnToggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
		
	};
	   
    var modalId = function (modal) {
        this.modalId = modal;
    };
    
    function setData(id, name){
        $("#supplierId").val(id);
        $("#supplierName").val(name);
        $("#" + this.modalId).modal('toggle');
    };
    
    return {
        
        init: function (modal, tableId, url, method) {
            modalId(modal);
            initTable(tableId, url, method);
        },

        setData: function (id, name) {
            setData(id, name);
        }
    };
}();