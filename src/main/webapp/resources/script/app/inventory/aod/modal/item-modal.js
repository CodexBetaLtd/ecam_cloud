var AodItemAddModal = function () {

    /*********************************************************************
     * Init Buttons
     *********************************************************************/

    var initButtons = function () {
        $('#btn-add-aod-item').on('click', function () {
            AodItemAddModal.addAODItem();
        });
    };

    /*********************************************************************
     * Init Custom Components
     *********************************************************************/
    
    var runAodItemPartInput = function () {
        $("#itemPartName").inputClear({
            placeholder: "Select a Part",
            btnMethod: "AodItemAddModal.aodItemPartModalView()",
            clearMethod: "AodItemAddModal.aodItemClearStockInput()",
        });
    };

    var runAodItemStockInput = function () {
        $("#itemStockBatchNo").inputClear({
            placeholder: "Select a Stock",
            btnMethod: "AodItemAddModal.aodItemStockModalView()",
        });
    };

    var clearStockInput = function () {
        $('#itemStockId').val("");
        $('#itemStockQuantity').val("");
        $('#itemStockBatchNo').val("");
    }

    /*********************************************************************
     * Init Modals
     *********************************************************************/

    var setModal = function (modalName) {
        this.modalName = modalName;
    };

    var aodItemPartModalView = function () {

        var bizId = $('#businessId').val();
        if (bizId != null && bizId > 0) {
            var $modal = $('#aod-item-child-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../aod/partmodalview';
                $modal.load(url, '', function () {
                    AODPartSelectModal.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Business First.");
        }
    	
    };

    var aodItemStockModalView = function () {

        var partId = $('#itemPartId').val();
        if (partId != null && partId > 0 && partId != undefined) {
            var $modal = $('#aod-item-child-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../aod/stockmodalview';
                $modal.load(url, '', function () {
                    AODStockSelectModal.init(partId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Part First. Try Again !");
        }
    	
    };


    /*********************************************************************
     * Init Validator
     *********************************************************************/
    
    var initValidator = function () {

        var form = $('#frm_aod_item');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);

        jQuery.validator.addMethod("greaterThanGRNQuantity", function (value, element) {
            return value > 0
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
                aodItemPartName: {
                    required: true
                },
                itemStockBatchNo: {
                    required: true
                },
                itemQuantity: {
                    required: true,
                    number: true,
                    greaterThanGRNQuantity: [],
                    remote: {
                        url: "../validate/stock/onhandqty",
                        type: "GET",
                        data: {
                            itemStockId: function () {
                                return $('#itemStockId').val()
                            }
                        }
                    }
                }
            },

            messages: {
                aodItemPartName: "Please Select a Part for AOD Item.",
                itemStockBatchNo: "Please Select a Stock.",
                itemQuantity: {
                    required: "Please Insert Item Quantity.",
                    greaterThanGRNQuantity: "0 is not valid for AOD Item Qunatity.",
                    remote: "Not Available Quantity in Stock."
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

    var addAODItem = function () {
        if ($('#frm_aod_item').valid()) { 
        	var aodItem = aodItemObj(); 
            if (!isItemAlreadyAdd(aodItem)) { 
            	AodItemTab.addItemToList(aodItem);  
                $('#'+ this.modalName).modal('toggle');  
                
            } else {
                alert('Item already Added. Please change it');
            } 
        }
    };  
    
    var aodItemObj = function() {
    	var aodItems = {}
    	
    	aodItems['index'] = CustomValidation.nullValueReplace($("#itemIndex").val());
		aodItems['id'] = CustomValidation.nullValueReplace($("#itemId").val());
		aodItems['stockId'] = CustomValidation.nullValueReplace($("#itemStockId").val());
        aodItems['stockBatchNo'] = CustomValidation.nullValueReplace($("#itemStockBatchNo").val());
        aodItems['partId'] = CustomValidation.nullValueReplace($("#itemPartId").val());
        aodItems['partName'] = CustomValidation.nullValueReplace($("#itemPartName").val());
    	aodItems['partCode'] = CustomValidation.nullValueReplace($("#aodItemPartCode").val());
    	aodItems['warehouseId'] = CustomValidation.nullValueReplace($("#itemStockWarehouseId").val());
		aodItems['warehouseName'] = CustomValidation.nullValueReplace($("#itemStockWarehouseName").val()); 
		aodItems['description'] = CustomValidation.nullValueReplace($("#itemDescription").val());
		aodItems['itemQuantity'] = CustomValidation.nullValueReplace($("#itemQuantity").val()); 
		aodItems['remainingQuantity'] = CustomValidation.nullValueReplace($("#itemStockOnHandQty").val()); 
		aodItems['version'] = CustomValidation.nullValueReplace($("#itemVersion").val()); 
		
		return aodItems;
	};

    var isItemAlreadyAdd = function (item) {
        for (var i = 0; i < aodItemList.length; i++) {
            if (aodItemList[i].index != item.index) {
                if (item.partId == aodItemList[i].partId) {
                    if (item.stockId == aodItemList[i].stockId) {
                        return true;
                    }
                }
            }
        }
        return false;
    };

    return {

        init: function (modalName) {
            setModal(modalName);
            runAodItemPartInput();
            runAodItemStockInput();
            initButtons();
            initValidator()
        },

        addAODItem: function () {
            addAODItem();
        },

        aodItemPartModalView: function () {
            aodItemPartModalView();
        },

        aodItemStockModalView: function () {
            aodItemStockModalView();
        },

        aodItemClearStockInput: function () {
            clearStockInput();
        }

    }
}();