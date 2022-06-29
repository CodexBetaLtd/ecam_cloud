var MeterReadingAddModal = function () {
	
	var initButtons = function () {
		
		$('#add-meter-reading-btn').on('click', function () {
			MeterReadingAddModal.addMeterReading();
        });
		
	};
	
	var initDropDown = function () {
		
		$("#woMeterReadingAssignedAssetId").select2({
            dropdownParent: $("#master-modal-datatable"),
            placeholder: "Please Select a Asset",
            allowClear : true
        }).change(function () {
        	initMeterReadingSelect($(this).val());
        }).trigger('change');
    	
        $("#woMeterReadingId").select2({
            dropdownParent: $("#master-modal-datatable"),
            placeholder: "Please Select a Meter Reading",
            allowClear : true
        });    	
        
    };
    
    var initAssetSelect = function () {
        var $as = $("#woMeterReadingAssignedAssetId");
        $as.find("option").remove();
        $as.append($('<option>', {value: ''}).text('Please Select a Asset.'));
        $.each(assets, function (index, obj) {
            $as.append($('<option>', {value: obj.id}).text(obj.name));
        });
    };

    var initMeterReadingSelect = function (assetId) {
    	if (assetId != null && assetId > 0) {
    		$.ajax({
                type: "GET",
                url: '../restapi/asset/getMeterReadingList?assetId=' + assetId,
                contentType: "application/json",
                dataType: 'json',
                success: function (result) {
                    var $au = $("#woMeterReadingId");
                    $au.find("option").remove();
                    $au.append($('<option>', {value: ''}).text('Please Select a Meter Reading.'));
                    $.each(result, function (index, obj) {
                        $au.append($('<option>', {value: obj.meterReadingId}).text(obj.meterReadingName));
                    });
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + " " + thrownError);
                },
                error: function (e) {
                    alert("Failed to load data");
                }
            });
    	}        
    };
	
	var initValidator = function () {
        var form = $('#meter-reading-add-frm');
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
            	woMeterReadingAssignedAssetId: {
                    required: true
                },
                woMeterReadingId: {
                    required: true
                }
            },
            messages: {
            	woMeterReadingAssignedAssetId: "Please Select a Asset",
            	woMeterReadingId: "Please Select a Meter Reading"
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
    
    var addMeterReading = function () {
    	if ( $("#meter-reading-add-frm").valid() ) {
    		MeterReadingTab.addMeterReading();
    	}
    };
	
	return {
		init: function () {
			initButtons();
			initDropDown();
			initAssetSelect();
            initValidator();
        },
        
        addMeterReading: function () {
        	addMeterReading();
        }

	};
	
}();
