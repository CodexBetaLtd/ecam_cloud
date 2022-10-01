/**
 * Created by neo89 on 12/8/16.
 */

var UserSite = function () {

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
            }
            else if (cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper) {
                ajax = true;
            }
            else if (JSON.stringify(request.order) !== JSON.stringify(cacheLastRequest.order) ||
                JSON.stringify(request.columns) !== JSON.stringify(cacheLastRequest.columns) ||
                JSON.stringify(request.search) !== JSON.stringify(cacheLastRequest.search)
            ) {
                ajax = true;
            }

            cacheLastRequest = $.extend(true, {}, request);

            if (ajax) {
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
                }
                else if ($.isPlainObject(conf.data)) {
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

    $.fn.dataTable.Api.register('clearPipeline()', function () {
        return this.iterator('table', function (settings) {
            settings.clearCache = true;
        });
    });


    var getUserSiteList = function () {

        $('#tbl_userSite_list').dataTable().fnDestroy();

        var oTable = $('#tbl_userSite_list').dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/setting/userSiteTable",
                pages: 5
            }),
            columns: [
                {
                    orderable: false, 
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {data: 'siteSiteName'}
            ],
            aoColumnDefs: [
            	{
            		"bSearchable": false, "aTargets": [ 0,2]
            	},
            	{
                targets: 2,	//index of column starting from 0
                data: "siteSiteId",
                render: function (data, type, full, meta) {
                    return "<div align='center'>" +
                        "<a onclick='SiteUser.getSiteUserList(" + data + ")' class='btn btn-xs btn-green tooltips' data-placement='top' data-original-title='Site users' >" +
                        "<i class='fa fa-users' ></i></a>\n\n" +
                        "</div>";

                }
            }],
            oLanguage: {
                "sLengthMenu": "Show _MENU_ Rows",
                "sSearch": "",
                "oPaginate": {
                    "sPrevious": "&laquo;",
                    "sNext": "&raquo;"
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
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination'
        });
        $('#tbl_userSite_list_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        // modify table search input
        $('#tbl_userSite_list_wrapper .dataTables_length select').addClass("m-wrap small");
        // modify table per page dropdown
        $('#tbl_userSite_list_wrapper .dataTables_length select').select2();
        // initialzie select2 dropdown
        $('#tbl_userSite_list_columnToggler input[type="checkbox"]').change(function () {
            /* Get the DataTables object again - this is not a recreation, just a get of the object */
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
    }

    return {
        init: function () {
            getUserSiteList();
        }
    };

}();