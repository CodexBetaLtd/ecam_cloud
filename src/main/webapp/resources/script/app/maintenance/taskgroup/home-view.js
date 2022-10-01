var TaskGroup = function () {

    $.fn.dataTable.pipeline = function (opts) {
        var conf = $.extend({
            pages: 5,
            url: '',
            data: null,
            method: 'GET'
        }, opts);

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
                json.draw = request.draw;
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
        
        var oTable = $('#tbl_task_group').DataTable({
        	responsive: true,
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/taskGroup/taskGroupHomeList",
                pages: 5
            }),
            columns: [
                {
                    orderable: false,
                    searchable: false, 
                    width:"4%",
                    defaultContent: '',
                    className: 'select-checkbox',
                    responsivePriority: 2,
                },
                {	
                	data: 'name',
                	responsivePriority: 2,
                },
                {
                	data: 'description',
                	responsivePriority: 4
                },
                {
                	data : 'businessName',
                	responsivePriority: 4
                }
            ],
            aoColumnDefs: [],
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
            sPaging: 'pagination',
            select: {
                style:    'multi',
                selector: 'td:first-child',
            },
            rowClick : {
                sId : 'id',
                sUrl: "../taskGroup/edit?id",
            },
        });
        $('#tbl_task_group_wrapper .dataTables_filter input').addClass( "form-control input-sm").attr("placeholder", "Search");
        $('#tbl_task_group_wrapper .dataTables_length select').addClass( "m-wrap small");
        $('#tbl_task_group_wrapper .dataTables_length select').select2();
        $('#tbl_task_group_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
        
        DataTableUtil.deleteRows(oTable, "delete", "taskGroup", "id"); 
    };

    return {
        init: function () {
            runDataTable();
        }
    };

}();