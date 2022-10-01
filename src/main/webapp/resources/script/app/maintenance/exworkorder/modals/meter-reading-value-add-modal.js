var MeterReadingValueAddModal = function () {
	
	var initButtons = function () {
		
		$('#add-meter-reading-value-btn').on('click', function () {
			MeterReadingValueAddModal.addMeterReadingValue();
        });
		
	};
	
	var initVariables = function (meterReadingIndex, meterReadingName, meterReadingId) {		
		$('#meterReadingIndex').val(meterReadingIndex);
        $('#meterReadingName').val(meterReadingName);
        $('#meterReadingId').val(meterReadingId);         
    };
	
	var initValidator = function () {
        var form = $('#meter-reading-value-add-frm');
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
            	meterReadingValue: {
                    required: true,
                    number: true
                }
            },
            messages: {
            	meterReadingValue: {
            		required: "Please Insert a Value.",
            		number : "Please Insert a numeric value."
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
    
    var addMeterReadingValue = function () {
    	if ( $("#meter-reading-value-add-frm").valid() ) {
    		MeterReadingTab.addMeterReadingValue();
    	}
    };
	
	return {
		init: function (meterReadingIndex, meterReadingName, meterReadingId) {
			initVariables(meterReadingIndex, meterReadingName, meterReadingId);
			initButtons();			
            initValidator();
        },
        
        addMeterReadingValue: function () {
        	addMeterReadingValue();
        }

	};
	
}();
