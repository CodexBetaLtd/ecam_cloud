var WarrantyAddModal = function () {
	
	 /*********************************************************************
     * Init Buttons
     *********************************************************************/

    var initButtons = function () {

        $('#add-warranty-btn').off('click').on('click', function () {
            WarrantyAddModal.addWarranty();
        });

    };

    /*********************************************************************
     * Init Components
     *********************************************************************/
    
	var runWarrantyTypeSelect = function () {
        $("#warrantyType").select2({
            placeholder: "Select a Warranty Type",
            allowClear: true,
            dropdownParent: $("#master-modal-datatable")
        });
    };
    
    var runProviderSelect = function () {
        $("#warrantyProviderId").select2({
            placeholder: "Select a Provider",
            allowClear: true,
            dropdownParent: $("#master-modal-datatable")
        });
    };
    
    var runWarrantyUsageTermTypeSelect = function () {
        $("#warrantyUsageTermType").select2({
            placeholder: "Select a Warranty Usage Term Type",
            allowClear: true,
            dropdownParent: $("#master-modal-datatable")
        });
    };
    
    var runMeterReadingUnitIdSelect = function () {
        $("#warrantyMeterReadingUnitId").select2({
            placeholder: "Select a Meter Reading Unit",
            allowClear: true,
            dropdownParent: $("#master-modal-datatable")
        });
    };
    
    var runWarrantyDatePicker = function () {
        $('#warrantyExpiryDate').datepicker({
            autoclose: true,
            container: '#warranty-picker-container'
        });
    };
    
    var initWarrantyUsageTermOnChangeEvent = function () {
    	divHideAccordingToTermType();
    	$('#warrantyUsageTermType').on('change', function () {
    		divHideAccordingToTermType();
        });
    };
    
    var divHideAccordingToTermType = function () {
    	if ($('#warrantyUsageTermType').val() == "METER_READING") {
        	$('#meterReadingDataDiv').fadeIn("slow");
        } else {
        	$('#warrantyMeterReadingUnitId').select2("val", "");
        	$('#warrantyMeterReadingValueLimit').val("");
        	$('#meterReadingDataDiv').hide();
        }
    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#asset-warranty-frm');
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
                warrantyType: {
                    required: true
                },
                warrantyProviderId: {
                    required: true
                },
                warrantyUsageTermType: {
                    required: true,
                },
                warrantyExpiryDate: {
                    required: true
                },
                warrantyMeterReadingValueLimit: {
                    required: function (element) {
                        if ($('#warrantyUsageTermType').val() == "METER_READING") {
                            return true;
                        }
                    }
                },
                warrantyMeterReadingUnitId: {
                    required: function (element) {
                        if ($('#warrantyUsageTermType').val() == "METER_READING") {
                            return true;
                        }
                    }
                },
                warrantyCertificateNo: {
                    required: true
                },
            },
            messages: {
                warrantyType: "Please Select a Type",
                warrantyProviderId: "Please Select a Provider",
                warrantyUsageTermType: "Please Select a Usage Term Type",
                warrantyExpiryDate: "Please Insert a Expiry Date",
                warrantyMeterReadingValueLimit: "Please Insert a Meter Reading Value Limit",
                warrantyMeterReadingUnitId: "Please Insert a Meter Reading Unit",
                warrantyCertificateNo: "Please Insert a Certificate No",
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
    
    var addWarranty = function () {
        if ($('#asset-warranty-frm').valid()) {
            TabWarranty.addWarranty();
        }
    };

    return {

        init: function () {
        	runWarrantyTypeSelect();
        	runProviderSelect();
        	runWarrantyUsageTermTypeSelect();
        	runWarrantyDatePicker();
        	runMeterReadingUnitIdSelect();
        	initWarrantyUsageTermOnChangeEvent();
        	initValidator();
        	initButtons();
        },
        
        addWarranty: function () {
            addWarranty();
        }
    };
}();