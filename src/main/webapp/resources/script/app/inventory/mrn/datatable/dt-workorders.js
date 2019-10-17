var MRNWorkOrders = function () {

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

    var getWorkOrderDataTable = function (bizId) {

        var table_name = "tbl_select_workorder";

        var oTable = $('#' + table_name).dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/workorder/tabledata-by-business?id=" + bizId,
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false, 
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
                {data: 'code'},
                {data: 'description'},
                { 
                    data: 'id'
                }],
            aoColumnDefs: [{
                data: "id",
                targets: 3,
                render: function (data, type, row, meta) {
                    return htmlBtn(data, type, row, meta);
                }
            }],
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
            bLengthChange: false
        });
        $('#' + table_name + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + table_name + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + table_name + '_wrapper .dataTables_length select').select2();
        $('#' + table_name + '_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });

    };

    var htmlBtn = function (id, type, row, meta) {
        var vars = [id, row.code];
        return ButtonUtil.getCommonBtnSelectWithMultipleVars("MRNWorkOrders.setMRNWorkOrderData", id, vars);
    };

    var setMRNWorkOrderData = function (id, name) {
        $("#mrnWoId").val(id);
        $("#mrnWoNo").val(name);
    };

    return {

        init: function (bizId) {
            getWorkOrderDataTable(bizId);
        },

        setMRNWorkOrderData: function (id, name) {
        	setMRNWorkOrderData(id, name);
        }

    };

}();