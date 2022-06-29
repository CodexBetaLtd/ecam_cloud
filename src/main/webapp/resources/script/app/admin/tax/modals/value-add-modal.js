var ValueAddModal = function () {
	
	var setModal = function (modalName) {
		this.modalName = modalName;
	}
	
	var initButtons = function () {

		$('#add-tax-value-btn').on('click', function () {
			ValueAddModal.addValue();
        });
		
	};
	

    var runDatePicker = function () {
    	$('.date-picker1').datepicker({
    		container: $('.date-picker1').closest("div"),
    		autoclose: true
    	});
        $('.date-picker2').datepicker({
            container: $('.date-picker2').closest("div"),
            autoclose: true
        });
    };
	var initDropDown = function () {
		
		$("#taskType").select2({
            dropdownParent: $("#" + this.modalName),
            placeholder: "Please Select a Task Type",
            allowClear: true
        })       
		
	};
	
	var initValidator = function () {
        var form = $('#value-add-frm');
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
            	taxFromDate: {
            		required: true
            	},
            	taxToDate: {
                    required: true
                },
                taxValue: {
                	number: true,
                	required: true
                }
            },
            messages: {
            	taxFromDate: "Please Insert a Validity Start date",
            	taxToDate: "Please Select a Validity End date",
            	taxValue: {
            		required: "Please Insert a Value",
            		number: "Please insert a numeric value",
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
    
    var addValue = function () {
    	
    	if ( $("#value-add-frm").valid() ) {   		
    		
    		var value = {};

        	if ($('#valueIndex').val() != null && $('#valueIndex').val() != "" && $('#valueIndex').val() >= 0 ) {
        		value = taxValues[$('#valueIndex').val()];
        	} else {   		
        		taxValues.push(value);
        	}
        	

        	CustomValidation.validateFieldNull(value, 'id', $('#valueId').val());
        	CustomValidation.validateFieldNull(value, 'version', $('#valueVersion').val());
        	CustomValidation.validateFieldNull(value, 'startDate', $('#taxFromDate').val());
        	CustomValidation.validateFieldNull(value, 'endDate', $('#taxToDate').val());
            CustomValidation.validateFieldNull(value, 'value', $('#taxVale').val());    
            CustomValidation.validateFieldNull(value, 'isCurrentRate', $('#currentValue').val());
            CustomValidation.validateFieldNull(value, 'taxType', $('#taxType').val());
        	
            ValueTab.initTaskTable();
            $("#" + this.modalName).modal('toggle');
    	}
    };
    
    var fillScheduledTask = function (taxValue) {
        $("#valueIndex").val(taxValue.index);
        $("#valueId").val(taxValue.id);
        $("#valueVersion").val(taxValue.version);
        $("#taxFromDate").val(taxValue.startDate);
        $("#taxToDate").val(taxValue.endDate);    
        $("#taxVale").val(taxValue.value);
        $("#currentValue").val(taxValue.isCurrentRate);
    };
	
	return {
		init: function (modalName) {
			setModal(modalName);
			initButtons();
			initDropDown();
            initValidator();
            runDatePicker();
        },
        
        addValue: function () {
        	addValue();
        },
        
        fillScheduledTask: function (task) {
        	fillScheduledTask(task)
        }

	};
	
}();