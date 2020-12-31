﻿/*********************************************************************
 * Estimate Asset DataTable
 *********************************************************************/
var dtAsset = function () {


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


    var getEstimateAssetTable = function (url, func) {
        var tableId = "estimate_asset_tbl";

        var oTable = $('#' + tableId).dataTable({
            // processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: url,
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false,
                width: "5%",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
                {data: 'name'},
                {data: 'code'},
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return '---cat---';
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return '---loc---';
                    }
                }
            ],
            aoColumnDefs: [{
                targets: 5,
                width: "10%",
                data: "id",
                render: function (data, type, row, meta) {
                	var vars=[data,row.name,row.description];
                    return "<div align='center'>" + ButtonUtil.getCommonBtnSelectWithMultipleVars(func, data, vars); 
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
            // set the initial value
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false
        });
        $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableId + '_wrapper .dataTables_length select').select2();
        $('#' + tableId + '_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });

        /*var commonSet=function () {
         $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
         $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
         $('#' + tableId + '_wrapper .dataTables_length select').select2();
         $('#' + tableId + '_column_toggler input[type="checkbox"]').change(function () {
         var iCol = parseInt($(this).attr("data-column"));
         var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
         oTable.fnSetColumnVis(iCol, (bVis ? false : true));
         });
         };*/
    };


    /*********************************************************************
     * Set Data Value
     *********************************************************************/
    var setEstimateItemAsset = function (id, name, description) {
    	$("#itemPartId").val(id);
    	$("#itemPartNo").val(name);
        $("#itemDescription").val(description);
    	$("#itemPartNo").attr('readonly', true);
    	$('#stackable-modal').modal("hide");
    };
    var setEstimateOptionAsset = function (id, name, description) {
        $("#optionPartId").val(id);
        $("#optionPartNo").val(name);
        $("#optionDescription").val(description);
        $("#optionPartNo").attr('readonly', true);
        $('#stackable-modal').modal("hide");
    };


    return {
        dtEstimateAsset: function (url, func) {
            getEstimateAssetTable(url, func);
        },


        /*********************************************************************
         * Set Data Value
         *********************************************************************/
        setEstimateItemAsset: function (id, name, description) {
            setEstimateItemAsset(id, name, description);
        },
        setEstimateOptionAsset: function (id, name, description) {
            setEstimateOptionAsset(id, name, description);
        }

    };
}();