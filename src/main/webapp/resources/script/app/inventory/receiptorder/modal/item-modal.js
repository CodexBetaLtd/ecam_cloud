var ItemAddModal = function () {
	
	var initButton = function(){
        $('#btn-add-receipt-item').on('click', function () {
        	ItemAddModal.addReceiptOrderItem(this);
        });
	}
	var runValidator = function() {

        var form = $('#receiptOrderItemForm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);
        jQuery.validator.addMethod("greaterThanZero", function (value, element) {   
          	return value>0
        }, "0 is not valid");
        $('#receiptOrderItemForm').validate(
		{
			errorElement : "span", // contain the error msg in a span tag
			errorClass : 'help-block',
			errorPlacement : function(error, element) { // render error placement for each input type
				if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
					error.insertAfter($(element).closest('.form-group').children('div').children().last());
				} else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
					error.insertAfter($(element).closest('.form-group').children('div'));
				} else {
					error.insertAfter(element);
					// for other inputs, just perform default behavior
				}
			},
			ignore : "",
			rules : {
				itemAssetName : {
					required : true
				},
				itemStockName : {
					required : true
				},
				itemQtyReceived :{
					required : true,
					number: true,
					greaterThanZero:[]
				},
				itemUnitPrice :{
					required : true,
					number: true,
					greaterThanZero:[]
				}
			},
			messages : {
				itemAssetName : "Please Select a Part",
				itemStockName : "Please Select a Stock",
				itemQtyReceived :{ 
					required:"Please Specify Quantity Received",
					number:"Please Enter numeric value only",
					greaterThanZero:"Received Quantity can not be 0"
					},
				itemUnitPrice : {
					required:"Please Specify Unit Price",
					number:"Please Enter numeric value only",
					greaterThanZero:"Unit Price can not be 0"
				}
			},
			invalidHandler : function(event, validator) { //display error alert on form submit
				successHandler.hide();
				errorHandler.show();
			},
			highlight : function(element) {
				$(element).closest('.help-block').removeClass('valid');
				// display OK icon
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
				// add the Bootstrap error class to the control group
			},
			unhighlight : function(element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error');
				// set error class to the control group
			},
			success : function(label, element) {
				label.addClass('help-block valid');
				// mark the current input as valid and display OK icon
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
			},

			submitHandler : function(form) {
				successHandler.show();
				errorHandler.hide();
				// submit form
				return true;
			}
		});
	};
	
	var initPartSelect = function () {
        $("#itemAssetName").inputClear({
            placeholder: "Select A Part",
            btnMethod: "receiptItemTab.receiptAssetView()",
        });
    };
    
	var initStockSelect = function () {
        $("#itemStockName").inputClear({
            placeholder: "Select A Stock",
            btnMethod: "receiptItemTab.receiptStockView()",
        });
    };
	
	var addReceiptOrderItem = function(obj) {
		var form = $('#receiptOrderItemForm');
		console.log(form.valid())
		if (form.valid()) {
			receiptItemTab.addItemToList(obj);
		}
	}


    return {

        init: function () {
        	initButton();
        	runValidator();
        	initPartSelect();
        	initStockSelect();
        },
        addReceiptOrderItem:function(obj){
        	addReceiptOrderItem(obj);
        }
    };
}();