/*********************************************************************
 * Work Order User DataTable
 *********************************************************************/
var dtWorkOrderUser = function () {

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

    //todo : Need to Change

    var dtUsers = function (func, businessId) { 
        
		var tableId = "wo_user_select_tbl";
		
        var oTable = $("#" + tableId).DataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/users/usersbybusinessid?id=" + businessId,
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
                {data: 'fullName'},
                {data: 'emailAddress'},
                {data: 'personalCode'},
                {data: 'businessName'}
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
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: func,
                aoData:[  
                    {
                        sName : "id",
                    }, {
                        sName : "fullName",
                        sRender : "EncodeDecodeComponent.getBase64().encode"
                    },
                ],
            },
        });

        $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableId + '_wrapper .dataTables_length select').select2();
        $('#' + tableId + '_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });

    };

    var woTaskGroupUser = function (obj, sVal) {
        
        var row_id = $(obj).closest("td").attr("id");
        
        $('#user_select_tbl').dataTable().fnDestroy();
        
        var oTable = $('#user_select_tbl').DataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/users/getuserlist",
                pages: 5
            }),
            columns: [
                {
                    orderable: false,
                    searchable: false,
                    width: "8%",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {data: 'fullName'},
                {data: 'emailAddress'},
                {data: 'personalCode'}
            ],
            aoColumnDefs: [{
                targets: 4,
                data: "id",
                render: function (data, type, row, meta) {
                    var _user_id = data;
                    var _user_name = row.fullName;
                    if (sVal == "assign") {
                        return "<div>" +
                            "<input type='hidden' id='row_id' value='" + row_id + "'>" +
                            "<input type='hidden' id='user_id' value='" + _user_id + "'>" +
                            "<input type='hidden' id='user_name' value='" + _user_name + "'>" +
                            "<button id='link" + data + "' data-dismiss='modal' type='button' class='btn btn-xs btn-blue btn-squared addTaskAssignedUser'> <i class='clip-arrow-right'></i> Add </button> </div>";
                    }
                    else if (sVal == "complete") {
                        return "<div>" +
                            "<input type='hidden' id='row_id' value='" + row_id + "'>" +
                            "<input type='hidden' id='user_id' value='" + _user_id + "'>" +
                            "<input type='hidden' id='user_name' value='" + _user_name + "'>" +
                            "<button id='link" + data + "' data-dismiss='modal' type='button' class='btn btn-xs btn-blue btn-squared addTaskCompletedUser'> <i class='clip-arrow-right'></i> Add </button> </div>";
                    }
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
                [5, 10, 15, 20, "All"]
            ],
            scrollY: "195px",
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: func,
                aoData:[  
                    {
                        sName : "id",
                    }, {
                        sName : "partName"
                    }, {
                        sName : "partId"
                    }, {
                        sName : "site"
                    }
                ],
            },
        });
        $('#user_select_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#user_select_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#user_select_tbl_wrapper .dataTables_length select').select2();
        $('#user_select_tbl_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });

    };

    return {
        getUserList: function (func, businessId) {
            dtUsers(func, businessId);
        },

        woTaskGroupUser: function (obj, sVal) {
            woTaskGroupUser(obj, sVal);
        }
    };

}();