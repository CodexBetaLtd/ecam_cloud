var WarrantyAddModal = function () {
	
	var initWarrantyTypeSelect = function () {
        $("#warrantyType").select2({
            placeholder: "Select a Warranty Type",
            allowClear: true,
            dropdownParent: $("#common-modal")
        });
    };
    
    var initProviderSelect = function () {
        $("#warrantyProviderId").select2({
            placeholder: "Select a Provider",
            allowClear: true,
            dropdownParent: $("#common-modal")
        });
    };
    
    var initWarrantyUsageTermTypeSelect = function () {
        $("#warrantyUsageTermType").select2({
            placeholder: "Select a Warranty Usage Term Type",
            allowClear: true,
            dropdownParent: $("#common-modal")
        });
    };
    
    var initMeterReadingUnitIdSelect = function () {
        $("#warrantyMeterReadingUnitId").select2({
            placeholder: "Select a Meter Reading Unit",
            allowClear: true,
            dropdownParent: $("#common-modal")
        });
    };
    
    var initWarrantyDatePicker = function () {
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

    return {

        init: function () {
        	initWarrantyTypeSelect();
        	initProviderSelect();
        	initWarrantyUsageTermTypeSelect();
        	initWarrantyDatePicker();
        	initMeterReadingUnitIdSelect();
        	initWarrantyUsageTermOnChangeEvent();
        }
    };
}();