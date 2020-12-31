/*********************************************************************
 * Work Order Asset DataTable
 *********************************************************************/
var dtWorkOrderAsset = function () {

	$.fn.dataTable.pipeline = function(opts) {
		// Configuration options
		var conf = $.extend({
			pages : 5, // number of pages to cache
			url : '', // script url
			data : null, // function or object with parameters to send to the
							// server
			// matching how `ajax.data` works in DataTables
			method : 'GET' // Ajax HTTP method
		}, opts);

		// Private variables for storing the cache
		var cacheLower = -1;
		var cacheUpper = null;
		var cacheLastRequest = null;
		var cacheLastJson = null;

		return function(request, drawCallback, settings) {
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
					"type" : conf.method,
					"url" : conf.url,
					"data" : request,
					"dataType" : "json",
					"cache" : false,
					"success" : function(json) {
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

    var dtAssetsByBusiness = function (func) {
        var url = "";
            url = "../../restapi/asset/machine-tabledata";
        var tableId = "asset_tbl";
        var oTable = $('#'+tableId).dataTable({
        	responsive: true,
        	"processing": true,
            "serverSide": true,
            "ajax": $.fn.dataTable.pipeline( {
                url: "../restapi/asset/machine-tabledata",
            	pages: 5
            } ),            
            columns : [ {
                orderable: false,
                searchable: false, 
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },{
     			data : 'name'     				
     		},{
     			data : 'code'
     		}],
            aoColumnDefs: [{
            	orderData: [1,5],
            	targets: 3, //index of column starting from 0
                data: "id", //this name should exist in your JSON response
                render: function (data, type, row, meta) {
                	var vars = [data, row.name];
               return ButtonUtil.getCommonBtnSelectWithMultipleVars(func, data, vars);
                }
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
                [1, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ], 
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination', 
//            sDom: 'Rlfrtip'
           
        });
        $('#'+tableId+'_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search"); 
        $('#'+tableId+'_wrapper .dataTables_length select').addClass("m-wrap small"); 
        $('#'+tableId+'_wrapper .dataTables_length select').select2(); 
        $('#'+tableId+'_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        })
//        $("#" + tableId).dataTable().fnDestroy();
//        var oTable = $('#' + tableId).dataTable({
//        	processing : true,
//            serverSide : true,
//            ajax : $.fn.dataTable.pipeline( {
//                url: url,
//            	pages: 5
//            }),
//            columns : [{
//                orderable: false,
//                searchable: false,
//                width: "2%",
//                render: function (data, type, row, meta) {
//                    return meta.row + meta.settings._iDisplayStart + 1;
//                }
//            },
//                {data: 'name'},
//                {data: 'code'}
//            ],
//            aoColumnDefs: [{
//                targets: 3, //index of column starting from 0
//                data: "id", //this name should exist in your JSON response
//                render: function (data, type, row, meta) {
//                	console.log(row)
//                    var vars = [data, row.name];
//                   // return ButtonUtil.getCommonBtnSelectWithMultipleVars(func, data, vars);
//                    // return "<a id='link" + data + "' onclick='woAssetTab.woAddAsset(" + data + ",\"" + row.name + "\",\"" + row.code + "\");' type='button' class='btn btn-primary btn-squared btn-xs' >Select</a>";
//                }
//            }],
//            oLanguage: {
//                sLengthMenu: "Show_MENU_Rows",
//                sSearch: "",
//                oPaginate: {
//                    sPrevious: "&laquo;",
//                    sNext: "&raquo;"
//                }
//            },
//            aaSorting: [
//                [1, 'asc']
//            ],
//            aLengthMenu: [
//                [5, 10, 15, 20, -1],
//                [5, 10, 15, 20, "All"] // change per page values here
//            ],
//            sPaginationType: "full_numbers",
//            sPaging: 'pagination',
//            bLengthChange: false
//        });
//        $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
//        $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
//        $('#' + tableId + '_wrapper .dataTables_length select').select2();
//        $('#' + tableId + '_column_toggler input[type="checkbox"]').change(function () {
//            /* Get the DataTables object again - this is not a recreation, just a get of the object */
//            var iCol = parseInt($(this).attr("data-column"));
//            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
//            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
//        });
    };


    
    return {

        dtAssetsByBusiness: function (func) {
            dtAssetsByBusiness(func);
        }
    };
}();