var InventoryReportView = function () {
	
	var initButtons = function() { 
	    $('#viewInventoryReport').on('click', function (event) {
	    	if ($('#frmReportInventory').valid()) {				
	    		InventoryDetailTable.initDataTable();
			}
	    });
	};

    /*********************************************************************
     *  Item
     *********************************************************************/
    var runItemTypeSelect = function () {
        $("#itemTypeId").select2({
            placeholder: "Select a Item Type",
            allowClear: true
        });
    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#frmReportInventory');
        
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        
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
            	itemType: {
                    required: true
                },
                toDate: {
                    required: true
                }
            },
            messages: {
            	itemType: "Please Select a select item Type",
                toDate: "Please Select a To date"
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
            runItemTypeSelect();
            initValidator();
        },

    };
}();