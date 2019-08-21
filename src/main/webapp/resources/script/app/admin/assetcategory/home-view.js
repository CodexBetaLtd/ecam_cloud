var AssetCategoryHome = function () {
	
	//
	// Pipelining function for DataTables. To be used to the `ajax` option of DataTables
	//
	$.fn.dataTable.pipeline = function ( opts ) {
	    // Configuration options
	    var conf = $.extend( {
	        pages: 5,     // number of pages to cache
	        url: '',      // script url
	        data: null,   // function or object with parameters to send to the server
	                      // matching how `ajax.data` works in DataTables
	        method: 'GET' // Ajax HTTP method
	    }, opts );
	 
	    // Private variables for storing the cache
	    var cacheLower = -1;
	    var cacheUpper = null;
	    var cacheLastRequest = null;
	    var cacheLastJson = null;
	 
	    return function ( request, drawCallback, settings ) {
	        var ajax          = false;
	        var requestStart  = request.start;
	        var drawStart     = request.start;
	        var requestLength = request.length;
	        var requestEnd    = requestStart + requestLength;
	         
	        if ( settings.clearCache ) {
	            // API requested that the cache be cleared
	            ajax = true;
	            settings.clearCache = false;
	        }
	        else if ( cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper ) {
	            // outside cached data - need to make a request
	            ajax = true;
	        }
	        else if ( JSON.stringify( request.order )   !== JSON.stringify( cacheLastRequest.order ) ||
	                  JSON.stringify( request.columns ) !== JSON.stringify( cacheLastRequest.columns ) ||
	                  JSON.stringify( request.search )  !== JSON.stringify( cacheLastRequest.search )
	        ) {
	            // properties changed (ordering, columns, searching)
	            ajax = true;
	        }
	         
	        // Store the request for checking next time around
	        cacheLastRequest = $.extend( true, {}, request );
	 
	        if ( ajax ) {
	            // Need data from the server
	            if ( requestStart < cacheLower ) {
	                requestStart = requestStart - (requestLength*(conf.pages-1));
	 
	                if ( requestStart < 0 ) {
	                    requestStart = 0;
	                }
	            }
	             
	            cacheLower = requestStart;
	            cacheUpper = requestStart + (requestLength * conf.pages);
	 
	            request.start = requestStart;
	            request.length = requestLength*conf.pages;
	 
	            // Provide the same `data` options as DataTables.
	            if ( $.isFunction ( conf.data ) ) {
	                // As a function it is executed with the data object as an arg
	                // for manipulation. If an object is returned, it is used as the
	                // data object to submit
	                var d = conf.data( request );
	                if ( d ) {
	                    $.extend( request, d );
	                }
	            }
	            else if ( $.isPlainObject( conf.data ) ) {
	                // As an object, the data given extends the default
	                $.extend( request, conf.data );
	            }
	 
	            settings.jqXHR = $.ajax( {
	                "type":     conf.method,
	                "url":      conf.url,
	                "data":     request,
	                "dataType": "json",
	                "cache":    false,
	                "success":  function ( json ) {
	                    cacheLastJson = $.extend(true, {}, json);
	 
	                    if ( cacheLower != drawStart ) {
	                        json.data.splice( 0, drawStart-cacheLower );
	                    }
	                    if ( requestLength >= -1 ) {
	                        json.data.splice( requestLength, json.data.length );
	                    }
	                     
	                    drawCallback( json );
	                }
	            } );
	        }
	        else {
	            json = $.extend( true, {}, cacheLastJson );
	            json.draw = request.draw; // Update the echo for each response
	            json.data.splice( 0, requestStart-cacheLower );
	            json.data.splice( requestLength, json.data.length );
	 
	            drawCallback(json);
	        }
	    }
	};
	 
	// Register an API method that will empty the pipelined data, forcing an Ajax
	// fetch on the next draw (i.e. `table.clearPipeline().draw()`)
	$.fn.dataTable.Api.register( 'clearPipeline()', function () {
	    return this.iterator( 'table', function ( settings ) {
	        settings.clearCache = true;
	    } );
	} );
	
    var runDataTable = function () {
    	assetCategoryTable = $('#asset_category_tbl').dataTable({
        	processing : true,
            serverSide : true,
            ajax : "../restapi/assetCategory/tabledata",
            columns : [          
            	 {
                    render: function (data, type, row, meta) {
                     return meta.row + meta.settings._iDisplayStart + 1;
                 } },
                 {data: 'name'},
                 {data: 'description'},
                 {data: 'parentName'}],
            aoColumnDefs: [
            		{"bSearchable" : false, "aTargets": [0, 4]}, 
            		{"orderable" : false, "aTargets": [0, 4]},
            		{"targets": 4,//index of column starting from 0
            			"data": "id", //this name should exist in your JSON response
            			"render": function ( data, type, full, meta ) {
            				return "<div align=\"center\"><div class=\"btn-group\">" +
                     			"<a class=\"btn btn-xs btn-blue dropdown-toggle btn-sm tooltips\"  data-placement=\"top\"data-original-title=\"remove or edit\"data-toggle=\"dropdown\" href=\"#\">"
                     			+"<i class=\"fa fa-cog\"></i><span class=\"caret\"></span>" +
                     			"</a><ul role=\"menu\" class=\"dropdown-menu pull-right\">"
                     			+ "<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" onclick=\"AssetCategoryHome.editCategory(" + data + ")\">" +
                     			"<i class=\"fa fa-edit\"></i> Edit</a>" +
                     			"</li><li role=\"presentation\"><a data-toggle=\"modal\" role=\"menuitem\" tabindex=\"-1\" href=\"#model"+data+"\">" +
                     			"<i class=\"fa fa-times\"></i> Remove</a></li></ul>" +
                     			"</div><div id=\"model" + data + "\" class=\"modal fade\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" style=\"display: none;\">"
                     			+ "<div class=\"modal-body\"><p>Are You Sure You want to delete Asset Category ?</p>" +
                     			"</div>" +
                     			"<div class=\"modal-footer\"><a data-dismiss=\"modal\" class=\"btn btn-green \" >"
                     			+ "Cancel</a> " +
                     			"<a onclick=\"AssetCategoryHome.deleteCategory(" + data + ")\" class=\"btn btn-red \">Delete</a></div></div></div>"; }
            		}],
            oLanguage: {
                "sLengthMenu": "Show _MENU_ Rows",
                "sSearch": "",
                "oPaginate": {
                    "sPrevious": "&laquo;",
                    "sNext": "&raquo;"
                }
            }, 
            "aaSorting": [
                [1, 'asc']
            ],
            "aLengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            
            // set the initial value
           iDisplayLength: 10,
            bAutoWidth: false,
        //     bLengthChange: false,
            sPaginationType: "full_numbers", 
            "initComplete": function(settings, json) {
		       
			  } 
        });
        $('#asset_category_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        // modify table search input
        $('#asset_category_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        // modify table per page dropdown
        $('#asset_category_tbl_wrapper .dataTables_length select').select2();
        // initialzie select2 dropdown
        $('#asset_category_tbl_toggler input[type="checkbox"]').change(function () {
            /* Get the DataTables object again - this is not a recreation, just a get of the object */
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });       
        
    };
    
    var reloadCategory = function () {		
		$.ajax({
            url: "../assetCategory/",
            type: 'GET',
            success: function(response) {
                $("#assetCategoryTab").empty().append(response);
	        	AssetCategoryHome.init();
            }
        });
	};
    
    var deleteCategory = function (id) {		
		$.ajax({
            url: "../assetCategory/delete?id="+ id,
            type: 'GET',
            success: function (response) {
            	$('#model' + id).modal('toggle');
                $("#assetCategoryTab").empty().append(response);
                AssetCategoryHome.init();
            }
        });
	};
	
	var deleteCategoryFromEditPage = function (id) {		
		$.ajax({
	        url: "../assetCategory/delete?id="+ id,
	        type: 'GET',
	        success: function(response) {
                $("#assetCategoryTab").empty().append(response);
                AssetCategoryHome.init();
	        }
	    });
	};
	
	var addCategory = function () {		
		 $.ajax({
            url: "../assetCategory/add",
            type: 'GET',
            success: function(response) {
                $("#assetCategoryTab").empty().append(response);
                AssetCategoryAdd.init();
            }
        });
	};
	
	var editCategory = function (id) {		
		 $.ajax({
           url: "../assetCategory/edit?id=" + id,
           type: 'GET',
           success: function(response) {
               $("#assetCategoryTab").empty().append(response);
               AssetCategoryAdd.init();
           }
       });
	};
    
    return {
    	
        init: function () {
        	runDataTable();
        },
        
        deleteCategory : function(id) {
			deleteCategory(id)
		},
		
		deleteCategoryFromEditPage : function(id) {
			deleteCategoryFromEditPage(id)
		},
		
		addCategory : function() {
			addCategory()
		},
		
		editCategory : function(id) {
			editCategory(id)
		},
		
		reloadCategory : function(){
			reloadCategory();
		}
    };
}();