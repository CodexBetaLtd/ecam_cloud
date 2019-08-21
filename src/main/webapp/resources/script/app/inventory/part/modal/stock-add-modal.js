var StockAddModal = function () { 
	
    var initBtnStockBusiness = function () {
        $("#warehouseName").inputClear({
            placeholder: "Warehouse ",
            btnMethod: "StockTab.getWarehouseByBusiness()",
            tooltip: "Select Warehouse"
        });
    };
    
    initBtnAddStock=function(){    	
        $('#btn-add-stock-part').on('click', function () {
        	StockAddModal.addPartStock(this);
        });
    };
    
    var initValidator = function () {
        var form = $('#frm_part_stock');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        
        jQuery.validator.addMethod("greaterThanZeroQuantity", function (value, element) {
            return value > 0
        }, "0 is not valid");

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
            	warehouseName: {
                    required: true
                },
                batchNo: {
                    required: true
                },
                qtyOnHand: {
                	required: true,
                	number:true,
                	greaterThanZeroQuantity: [],
                },
                minQty: {
                    required: true,
                    number:true,
                    greaterThanZeroQuantity: [],
                }
            },

            messages: {
            	warehouseName: "Please Select a Warehouse",
            	batchNo: "Please Insert Batch No",
            	qtyOnHand: {
            		required: "Please Insert Item Quantity",
            		greaterThanZeroQuantity: "0 is not valid for Qunatity",
            		number:"Please Enter Numeric Values only"
            	},
            	minQty: {
                    required: "Please Insert Item Minimum Quantity",
                    greaterThanZeroQuantity: "0 is not valid for Minimum Qunatity",
                    number:"Please Enter Numeric Values only"
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
    var setWarehouse=function(id,name){
    	$('#warehouseId').val(id);
    	$('#warehouseName').val(name);    	
    }
    var addPartStock = function (obj) {
        if ($('#frm_part_stock').valid()) {      
        	StockTab.addNewOrUpdatePartStock();
        	$('#stock-modal').modal('toggle');
        }
    };
    
    return {
    	
        init: function () {
        	initBtnStockBusiness();
        	initBtnAddStock();
            initValidator();
        },
        
        addPartStock:function(obj){
        	addPartStock(obj)
        },
        setWarehouse:function(id,name){
        	setWarehouse(id,name)
        }
    }
}();