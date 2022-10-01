var AssetEventAddModal = function () {
	
	var initButtons = function () {
		
		$('#btn-add-asset-event').on('click', function () {
			AssetEventAddModal.addAssetEvent();
	    });
		
	};
    
    var initAssetEventTypeSelect = function(){
    	$("#assetEventTypeName").inputClear({
    		placeholder:"Select a Event Type",
        	btnMethod:"AssetEventAddModal.assetEventTypeView()",
        	tooltip: "Asset Event",
    	});
    };    
    
    var setAssetEventType = function(id, name){
    	$('#assetEventTypeId').val(id);
    	$('#assetEventTypeName').val(name);
    	$('#asset-event-add-modal').modal('toggle');
    };
    
    var assetEventTypeView = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {	 
	        var $modal = $('#asset-event-add-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../../asset/asseteventtypeview';
	            $modal.load(url, '', function () {
	            	AssetEventTypeDataTable.init(bizId);
	                $modal.modal();
	            });
	        }, 1000);
        } else {
			alert("Please Select a Business First.");
		} 
    };
    
    var addAssetEvent = function () {    	
    	if ( $('#asset_event_add_frm').valid() ) {
    		TabAssetEvent.addAssetEvent();
    	}    	
    };
    
    var initValidator = function () {
        var form = $('#asset_event_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
            	if (element.attr("type") == "radio" || element.attr("type") == "checkbox" ) { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length ){
                	error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
                assetEventTypeName: {
                    required: true
                },
                assetEventDescription: {
                    required: true
                }
            },
            messages: {
                assetEventTypeName: "Please Select a Event Type",
                assetEventDescription: "Please Insert a Description"
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
            },
            success: function (label, element) {
                label.addClass('help-block valid');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
            },
            submitHandler: function (form) {
                successHandler.show();
                errorHandler.hide();
                return true;
            }
        });
    };

    return {
        init: function () {
        	initButtons();
            initAssetEventTypeSelect();  
            initValidator();
        },
        
        assetEventTypeView : function () {
        	assetEventTypeView();
        },
        
        setAssetEventType: function (id, name){
        	setAssetEventType(id, name);
        },
        
        addAssetEvent : function () {
        	addAssetEvent();
        }
    };

}();