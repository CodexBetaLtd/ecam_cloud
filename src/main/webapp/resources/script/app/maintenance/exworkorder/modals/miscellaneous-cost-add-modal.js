var MiscCostAddModal = function () {
	
	var initButtons = function () {
		
		$('#add-misc-cost-btn').on('click', function () {
			MiscCostAddModal.addMiscCost();
        });
		
	};
	
	var initDropdown = function () {
		
        $("#miscellaneousExpenseType").select2({
            dropdownParent: $("#master-modal-datatable"),
            placeholder: "Select a Misc. Expense Type",
            allowClear : true
        });
        
    };
    
    var initMiscCostAutoCalculation = function () {
        $("#miscellaneousExpenseEstQuantity, #miscellaneousExpenseEstUnitCost, #miscellaneousExpenseActualQuantity, #miscellaneousExpenseActualCost").keyup(function () {
            $('#miscellaneousExpenseEstTotal').val($('#miscellaneousExpenseEstQuantity').val() * $('#miscellaneousExpenseEstUnitCost').val());
            $('#miscellaneousExpenseActualTotal').val($('#miscellaneousExpenseActualQuantity').val() * $('#miscellaneousExpenseActualCost').val());
        });
    };
	
	var initValidator = function () {
        var form = $('#misc-cost-add-frm');
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
            	miscellaneousExpenseType: {
                    required: true
                },
                miscellaneousExpenseDescription: {
                    required: true
                },
                miscellaneousExpenseEstQuantity: {
                	number: true,
                    required: true
                },
                miscellaneousExpenseEstUnitCost: {
                	number: true,
                    required: true
                },
                miscellaneousExpenseActualQuantity: {
                	number: true,
                    required: true
                },
                miscellaneousExpenseActualCost: {
                	number: true,
                    required: true
                }
            },
            messages: {
            	miscellaneousExpenseType: "Please Select a Misc Type.",
            	miscellaneousExpenseDescription: "Please Insert a Description",
            	miscellaneousExpenseEstQuantity: {
            		required: "Please Insert a Estimate Quantity",
            		number: "Please Insert a Numeric Value"
            	},
            	miscellaneousExpenseEstUnitCost: {
            		required: "Please Insert a Estimate Unit Cost",
            		number: "Please Insert a Numeric Value"
            	},
            	miscellaneousExpenseActualQuantity: {
            		required: "Please Insert a Actual Quantity",
            		number: "Please Insert a Numeric Value"
            	},
            	miscellaneousExpenseActualCost: {
            		required: "Please Insert a Actual Cost",
            		number: "Please Insert a Numeric Value"
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
    
    var addMiscCost = function () {
    	if ( $("#misc-cost-add-frm").valid() ) {
    		MiscCostTab.addMiscCost();
    	}
    };
	
	return {
		init: function () {
			initButtons();	
			initDropdown();
			initMiscCostAutoCalculation();
            initValidator();
        },
        
        addMiscCost: function () {
        	addMiscCost();
        }

	};
	
}();
