﻿var MachineHome = function () {
	
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
	
    var initAssetDataTable = function () {
    	
        var oTable = $('#machine_tbl').DataTable({
        	responsive: true,
        	"processing": true,
            "serverSide": true,
            "ajax": $.fn.dataTable.pipeline( {
                url: "../../restapi/asset/machine-tabledata",
            	pages: 5
            } ),            
            columns : [ {
                orderable: false,
                searchable: false,
                width: "4%",
                defaultContent: '',
                className: 'select-checkbox',
                responsivePriority: 2, 
            },{
     			data : 'name',
     			responsivePriority: 2      				
     		},{
     			data : 'code',
     			responsivePriority: 4   
     		},{
     			data : 'assetCategoryName',
     			responsivePriority: 4   
     		},{ 
     			data : 'customerName',
     			responsivePriority: 4   
     		},{ 
                data : 'parentAssetName',
                responsivePriority: 4   
            },{ 
                data : 'siteName',
                responsivePriority: 4   
            },{ 
                data : 'subSiteName',
                responsivePriority: 4   
            },{ 
                data : 'department',
                responsivePriority: 4   
            }],
            aoColumnDefs: [{
            	orderData: [1,5]
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
            select: {
                style:    'multi',
                selector: 'td:first-child',
            },
            rowClick : {
                sId : 'id',
                sUrl: "../../asset/machine/edit?id",
            },
//            sDom: 'Rlfrtip'
           
        });
        $('#machine_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search"); 
        $('#machine_tbl_wrapper .dataTables_length select').addClass("m-wrap small"); 
        $('#machine_tbl_wrapper .dataTables_length select').select2(); 
        $('#machine_tbl_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });  
        
        DataTableUtil.deleteRows(oTable, "delete", "../asset/machine", "id");
        
    };    
    
    return { 
        init: function () {
        	initAssetDataTable();
        }	
    };
}();