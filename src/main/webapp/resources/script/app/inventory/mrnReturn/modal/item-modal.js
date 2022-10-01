var MRNReturnItemAddModal = function () {

    /*********************************************************************
     * Init Buttons
     *********************************************************************/

    var initButtons = function () {
        $('#btn-add-mrn-return-item').on('click', function () {
        	MRNReturnItemAddModal.addMRNItem();
        });
    };

    /*********************************************************************
     * Init Custom Components
     *********************************************************************/
    
    var runMrnItemInput = function () {
        $("#mrnItemName").inputClear({
            placeholder: "Select a Part",
            btnMethod: "MRNReturnItemAddModal.mrnItemModalView()",
            clearMethod: "MRNItemAddModal.aodItemClearStockInput()",
        });
    };

    /*********************************************************************
     * Init Modals
     *********************************************************************/

    var setModal = function (modalName) {
        this.modalName = modalName;
    };

    var mrnItemModalView = function () {
        var mrnId = $('#mrnId').val();
        if (mrnId != null && mrnId > 0) {
            var $modal = $('#mrn-item-child-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../mrnReturn/mrnItemmodalview';
                $modal.load(url, '', function () {
                	MRNItemSelectModal.init(mrnId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a MRN First.");
        }
    	
    };



    /*********************************************************************
     * Init Validator
     *********************************************************************/
    
    var initValidator = function () {

        var form = $('#frm_mrn_return_item');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);

        jQuery.validator.addMethod("greaterThanZeroMRNReturnQuantity", function (value, element) {
        	return value > 0
        }, "This item quantity unable to return for seleted AOD");
        
        jQuery.validator.addMethod("greaterThanRemainMRNReturnQuantity", function (value, element) {
            return value <=parseFloat($("#mrnItemRemainigQty").val())
        }, "This item quantity unable to return for seleted AOD");

        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length) {
                    error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
            	mrnItemName: {
                    required: true
                },
                mrnItemReturnQuantity: {
                    required: true,
                    number: true,
                    greaterThanZeroMRNReturnQuantity: [],
                greaterThanRemainMRNReturnQuantity: []

                }
            },

            messages: {
            	mrnItemName: "Please Select a MRN Item.",
            	mrnItemReturnQuantity: {
                    required: "Please Insert Return Quantity.",
                    greaterThanZeroMRNReturnQuantity: "0 is not valid for MRN Item Qunatity.",
                    greaterThanRemainMRNReturnQuantity: "Unable to return MRN Item Qunatity.Remaining Qunatity exceed",
                    number: "Please Insert numeric value only"
                }
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
            unhighlight: function (element) { // revert the change done by highlight
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

    var addMRNItem = function () {
        if ($('#frm_mrn_return_item').valid()) { 
        	var aodItem = mrnItemObj(); 
            if (!isItemAlreadyAdd(aodItem)) { 
            	MRNReturnItemTab.addItemToList(aodItem);  
               $('#'+ this.modalName).modal('toggle');  
                
            } else {
                alert('Item already Added. Please change it');
            } 
        }
    };  
    
    var mrnItemObj = function() {
    	var mrnItems = {}
    	
    	mrnItems['index'] = CustomValidation.nullValueReplace($("#itemIndex").val());
		mrnItems['id'] = CustomValidation.nullValueReplace($("#itemId").val());
        mrnItems['mrnItemId'] = CustomValidation.nullValueReplace($("#mrnItemId").val());
        mrnItems['partName'] = CustomValidation.nullValueReplace($("#mrnItemName").val());
		mrnItems['description'] = CustomValidation.nullValueReplace($("#mrnReturnItemDescription").val());
		mrnItems['itemReturnQuantity'] = CustomValidation.nullValueReplace($("#mrnItemReturnQuantity").val()); 
		mrnItems['itemQuantity'] = CustomValidation.nullValueReplace($("#mrnItemRemainigQty").val()); 
		mrnItems['version'] = CustomValidation.nullValueReplace($("#itemVersion").val()); 
		
		return mrnItems;
	};

    var isItemAlreadyAdd = function (item) {
    	console.log(item)
        for (var i = 0; i < mrnItemList.length; i++) {
            if (mrnItemList[i].index != item.index) {
                if (item.mrnItemId == mrnItemList[i].mrnItemId) {
                        return true;
                }
            }
        }
        return false;
    };

    return {

        init: function (modalName) {
            setModal(modalName);
            runMrnItemInput();
            initButtons();
            initValidator()
        },

        addMRNItem: function () {
            addMRNItem();
        },

        mrnItemModalView: function () {
        	mrnItemModalView();
        },

        mrnItemStockModalView: function () {
            mrnItemStockModalView();
        },

        mrnItemClearStockInput: function () {
            clearStockInput();
        }

    }
}();