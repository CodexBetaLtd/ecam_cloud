/*******************************************************************************
 * Scheduled Maintenance Part
 ******************************************************************************/
var PartAddModal = function () {
	
	var initButtons = function () {
		
		$('#add-part-btn').on('click', function () {
			PartAddModal.addPart();
        });
		
	};
	
	var initDropDown = function () {
		
		$("#woPartAssetId").select2({
        	dropdownParent: $("#master-modal-datatable"),
			placeholder : "Please Select a Asset",
			allowClear : true            
		}).change(function () {
            var assetId = $("#woPartAssetId option:selected").val();            
            $("#woPartTaskIndex").empty();
            $("#woPartTaskIndex").append($("<option></option>").attr("value", '').text('Please Select a Task'));        
            $.each(tasks, function (index, task) {
            	if ( task.assignedAssetId == assetId ) {
            		$("#woPartTaskIndex").append($("<option></option>").attr("value", task.index).text(task.name));
            	}
            });
        }).trigger('change');
		
		$("#woPartTaskIndex").select2({
            dropdownParent: $("#master-modal-datatable"),
            placeholder: "Please Select a Task",
            allowClear: true
        })       
		
	};
	
	var initAssetSelect = function () {
        $("#woPartAssetId").empty();
        $("#woPartAssetId").append($("<option></option>").attr("value", '').text('Please Select a Asset'));
        $.each(assets, function (index, obj) {
        	$("#woPartAssetId").append($("<option></option>").attr("value", obj.id).text(obj.name));
        });
    };

    var initStockSelect = function () {
        $("#woPartPartName").inputClear({
            placeholder: "Select Stock Part",
            btnMethod: "PartAddModal.stockSelectModal()",
            tooltip: "Select Stock"
        });
    };

    var stockSelectModal = function () {
    	if ( $('#woPartAssetId').val() != null && $('#woPartAssetId').val() != undefined && 
    			$('#woPartAssetId').val() != "" && $('#woPartAssetId').val() > 0 ) {
    		var $modal = $('#select-pages-open-modal');
    		CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../workorder/stock-select-model-view';
                $modal.load(url, '', function () {
                    StockSelectModal.init($('#woPartAssetId').val(), "PartAddModal.setStock");
                    $modal.modal();
                });
            }, 1000);
    	} else {
    		 alert("Please Select a Asset First.");
    	}		
	};

    var setStock = function (id, partName, partId, site) {
        $('#woPartStockId').val(id);
        $('#woPartPartName').val(EncodeDecodeComponent.getBase64().decode(partName));
		$('#woPartPartId').val(partId);
        $('#woPartLocation').val(EncodeDecodeComponent.getBase64().decode(site));
        
		$('#select-pages-open-modal').modal('toggle');
	};
	
	var initValidator = function () {
        var form = $('#part-add-frm');
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
            	woPartAssetId: {
                    required: true
                },
                woPartTaskIndex: {
                    required: true
                },
                woPartEstimatedQuantity: {
                	number: true,
                    required: true
                },
                woPartPartName: {
                    required: true
                }
            },
            messages: {
            	woPartAssetId: "Please Select a Asset",
            	woPartTaskIndex: "Please Select a Task",
            	woPartEstimatedQuantity: {
                	number : "Please Insert a numeric value",
                	required : "Please Insert a Quantity"
                },                
                woPartPartName: "Please Select a Part"
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                // display OK icon
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
                // add the Bootstrap error class to the control group
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
                // set error class to the control group
            },
            success: function (label, element) {
                label.addClass('help-block valid');
                // mark the current input as valid and display OK icon
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
            },
            submitHandler: function (form) {
                successHandler.show();
                errorHandler.hide();
                return true;
            }
        });
    };
    
    var addPart = function () {
    	if ( $("#part-add-frm").valid() ) {
    		PartTab.addPart();
    	}
    };
	
	return {
		
		init: function () {
			initButtons();
			initDropDown();
            initAssetSelect();
            initStockSelect();
            initValidator();
        },  
        
	    stockSelectModal: function(assetId) {
	    	stockSelectModal(assetId);
        },

        setStock: function (id, partName, partId, site) {
            setStock(id, partName, partId, site);
        },
        
        addPart: function () {
        	addPart();
        }

	};
	
}();
