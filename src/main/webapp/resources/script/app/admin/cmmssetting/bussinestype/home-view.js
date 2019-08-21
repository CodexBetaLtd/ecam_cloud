var BussinessTypeHome = function() {

	//
	// Pipelining function for DataTables. To be used to the `ajax` option of DataTables
	//
	$.fn.dataTable.pipeline = function(opts) {
		// Configuration options
		var conf = $.extend({
			pages : 5, // number of pages to cache
			url : '', // script url
			data : null, // function or object with parameters to send to the server
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
					// As a function it is executed with the data object as an arg
					// for manipulation. If an object is returned, it is used as the
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

	// Register an API method that will empty the pipelined data, forcing an Ajax
	// fetch on the next draw (i.e. `table.clearPipeline().draw()`)
	$.fn.dataTable.Api.register('clearPipeline()', function() {
		return this.iterator('table', function(settings) {
			settings.clearCache = true;
		});
	});

	var runDataTable = function() {
		$('#bussiness_type_tbl').dataTable().fnDestroy();
		var oTable = $('#bussiness_type_tbl').dataTable( {
			"processing" : true,
			"serverSide" : true,
			"ajax" : $.fn.dataTable
					.pipeline({
						url : "../restapi/lookuptable/tabledataBussinessType",
						pages : 5
					}),
			columns : [
					{
						orderable: false, 
						searchable: false, 
						render : function(data, type, row, meta) {
							return meta.row + meta.settings._iDisplayStart + 1;
						}
					}, {
						data : 'allParent'
					}, {
						data : 'businessTypeDefinitionName'
					}, {
						data : 'businessTypeDefinitionShort'
					},{
						data : 'businessName'
					}, { 
						data : 'id'
					} ],
			"aoColumnDefs" : [ 
					{
						"targets" : 5,//index of column starting from 0
						"data" : "id", //this name should exist in your JSON response

						"render" : function(data, type, full, meta) {
                            return ButtonUtil.getEditBtnWithURL( 'businessType', data, 'BussinessTypeHome');
						}

					} ],
			oLanguage : {
				"sLengthMenu" : "Show _MENU_ Rows",
				"sSearch" : "",
				"oPaginate" : {
					"sPrevious" : "&laquo;",
					"sNext" : "&raquo;"
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ],
			"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ],
					[ 5, 10, 15, 20, "All" ] // change per page values here
			],
			dom : "<'row'<'col-sm-4 dtblbusinessType'><'col-sm-8'f>>"
					+ "<'row'<'col-sm-12'tr>>"
					+ "<'row'<'col-sm-6'i><'col-sm-6'p>>",
			initComplete : function() {
				$("div.dtblbusinessType")
						.html("<button class='btn btn-default btn-sm active tooltips' data-toggle='modal' type='button' id='business-type-new'><i class='clip-plus-circle-2  btn-new'></i> New</button>");
			},
			// set the initial value
			bAutoWidth : false,
			sScrollXInner : "100%",
			iDisplayLength : 10,
			bLengthChange : false,
			sPaginationType : "full_numbers",
			sPaging : 'pagination'
		});
		$('#bussiness_type_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
		// modify table search input
		$('#bussiness_type_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
		// modify table per page dropdown
		$('#bussiness_type_tbl_wrapper .dataTables_length select').select2();
		// initialzie select2 dropdown
		$('#bussiness_type_tbl_column_toggler input[type="checkbox"]').change(
		function() {
			/* Get the DataTables object again - this is not a recreation, just a get of the object */
			var iCol = parseInt($(this).attr("data-column"));
			var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
			oTable.fnSetColumnVis(iCol, (bVis ? false : true));
		});
	};

	var addModal = function() {
		var $modal = $('#cmms-setting-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();

		setTimeout(function() {
			var url = '../businessType/add';
			$modal.load(url, '', function() {
				$modal.modal();
				BussinesTypeAdd.init();
			});
		}, 1000);
	};

	var editModal = function(id) {
		var $modal = $('#cmms-setting-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();

		$.ajax({
			url : '../businessType/edit?id=' + id,
			data : id,
			success : function(response) {
				$modal.empty().append(response);
				$modal.modal();
				BussinesTypeAdd.init();
			},
			error : function(response) {
				console.log(response);
			}
		});

	};

	var saveModal = function() {
		var $modal = $('#cmms-setting-add-modal');
		var form = $('#bussinesTypeForm');

		if (form.valid()) {

			$.ajax({
				url : form.attr('action'),
				type : 'POST',
				data : form.serialize(),
				success : function(response) {
					$modal.empty().append(response);
					$modal.modal();
					BussinesTypeAdd.init();
					runDataTable();
				},
				error : function(response) {
					console.log(response);
				}
			});

		}
	};

	var deleteModal = function(id) {

		$.ajax({
			url : '../businessType/delete?id=' + id,
			data : id,
			success : function(response) {
				runDataTable();
			},
			error : function(response) {
				console.log(response);
			}
		});
	};
	
	var deleteInsideModal = function(id) {
		var $modal = $('#cmms-setting-add-modal');
		$.ajax({
			url : '../businessType/delete?id=' + id,
			success : function(response) {
				closeModal();
				runDataTable();
			},
			error : function(response) {
				console.log(response);
			}
		});
	};

	var closeModal = function() {
		$('#cmms-setting-add-modal').modal('toggle');
	};
	
	return {
		//main function to initiate template pages
		init : function() {
			runDataTable();
		},
		addModal : function() {
			addModal();
		},
		editModal : function(id) {
			editModal(id);
		},
		saveModal : function() {
			saveModal();
		},
		closeModal : function() {
			closeModal();
		},
		deleteModal : function(id) {
			deleteModal(id);
		},
		deleteInsideModal : function(id) {
			deleteInsideModal(id);
		}
	};
}();