var TriggerAddModal = function () {

    var initButtons = function () {
    	
        $('#add-trigger-btn').on('click', function () {
        	TriggerAddModal.addTrigger(); 
        });

    };
    
    var addTrigger = function() {

    	addValidateRules();   

    	if ( $('#schedule_maintenance_trigger_frm').valid() ) {				
    		TabScheduling.addTrigger();
		} else {			
			if($('#ttStartDate').closest('div').closest('.form-group').hasClass('has-error') || $('#ttEndDate').closest('div').closest('.form-group').hasClass('has-error')){
				 $('#howlong').html('<a href="#howlong_tab" data-toggle="tab" style="color:#a94442"> How Long <span class="symbol required"></span> </a>');
			} else {
				$('#howlong').html('<a href="#howlong_tab" data-toggle="tab"> How Long </a>');
			}
		}
    	
	};

    var initDivs = function () {
        if (assets.length == 0) {
            $("#schedulingnotasset").show();
            $("#schedulingasset").hide();
        } else {
            $("#schedulingasset").show();
            $("#schedulingnotasset").hide();
        }
    }

    var initYearlyMonthSelect = function () {
        $("#ttYearlyMonth").select2({
            placeholder: "Select a Month",
            allowClear: true,
            orientation:"auto top",
            dropdownParent: $("#trigger-modal")
        });
    };

    var initAssetMeterReadingSelect = function () {
    	$("#mrtAssetMeterReadingId").select2({
    		placeholder: "Select a Asset Meter Reading",
    		allowClear: true,
    		dropdownParent: $("#trigger-modal")
    	});
    };
    var initAssetMeterReadingSelect2 = function () {
        $("#mrtAssetMeterReadingId2").select2({
            placeholder: "Select a Asset Meter Reading",
            allowClear: true,
            dropdownParent: $("#trigger-modal")
        });
    };

    var initMeterReadingLogicSelect = function () {
    	$("#mrtLogicType").select2({
    		placeholder: "Select a Condition",
    		allowClear: true,
    		dropdownParent: $("#trigger-modal")
    	});
    };
    
    var initMeterReadingABCTypeSelect = function () {
        $("#abcTypes").select2({
            placeholder: "Select a Type",
            allowClear: true,
            dropdownParent: $("#trigger-modal")
        });
    };

    var initAssetEventTypeAssetSelect = function () {
    	$("#etAssetEventTypeAssetId").select2({
    		placeholder: "Select a Asset Event",
    		allowClear: true,
    		dropdownParent: $("#trigger-modal")
    	});
    };
    var initABCTypeSelect = function () {
        $("#parentTrigerId").select2({
            placeholder: "Select a Parent Trigger Type",
            allowClear: true,
            dropdownParent: $("#trigger-modal")
        });
    };

    var initDatePicker = function () {
        $('#trigger-modal .date-picker').datepicker({
            autoclose: true,
            orientation:"auto top",
            startDate: '-0m',
            
            container: '#picker-container'
        });
    };

    function initTriggerAssetSelect() {
        var data = [];
        for (var j = 0; j < assets.length; j++) {
            var obj = {};
            obj['id'] = assets[j].id;
            obj['text'] = assets[j].name;
            data.push(obj);
        };

        $("#triggerAssetId").select2({
            data: data,
            placeholder: "Select a Asset",
            allowClear: true,
            dropdownParent: $("#trigger-modal")
        }).change(function () {
            resetAssetMeterReading();
            resetAssetEvents();
        }).trigger("change");
    };

    var resetAssetMeterReading = function () {
        $('#mrtAssetMeterReadingId').find("option").remove();
        setAssetMeterReading($('#triggerAssetId').val());
    };

    var resetAssetEvents = function () {
        $('#etAssetEventTypeAssetId').find("option").remove();
        setAssetEvents($('#triggerAssetId').val());
    };

    var setAssetMeterReading = function (assetId) {
        $.ajax({
            type: "GET",
            url: "../scheduledmaintenance/assetmeterreadingsbyasset/" + assetId,
            contentType: "application/json",
            dataType: "json",
            success: function (output) {
            	$('#mrtAssetMeterReadingId').append($('<option>', {value: ""}).text(""));
            	$.each(output, function (key, meterReading) {
            		$('#mrtAssetMeterReadingId').append($('<option>', {value: meterReading.meterReadingId}).text(meterReading.meterReadingViewName));
            	});
            	$('#mrtAssetMeterReadingId').trigger("change")
                $('#mrtAssetMeterReadingId2').append($('<option>', {value: ""}).text(""));
                $.each(output, function (key, meterReading) {
                    $('#mrtAssetMeterReadingId2').append($('<option>', {value: meterReading.meterReadingId}).text(meterReading.meterReadingViewName));
                });
                $('#mrtAssetMeterReadingId2').trigger("change")
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            },
            error: function (e) {
                console.log("Failed to load Meter Reading load");
            }
        });
    };

    var setAssetEvents = function (assetId) {
        $.ajax({
            type: "GET",
            url: "../scheduledmaintenance/asseteventtypeassetsbyasset/" + assetId,
            contentType: "application/json",
            dataType: "json",
            success: function (output) {
                $('#etAssetEventTypeAssetId').append($('<option>', {value: ""}).text(""));
                $.each(output, function (key, value) {
                    $('#etAssetEventTypeAssetId').append($('<option>', {value: value.id}).text(value.assetEventTypeName));
                });
                $('#etAssetEventTypeAssetId').trigger("change")
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            },
            error: function (e) {
                console.log("Failed to load Asset event load");
            }
        });
    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#schedule_maintenance_trigger_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);

        jQuery.validator.addMethod("minDate", function(value, element) { 
        	  return minDate();
        }, '');
        
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
            	triggerAssetId: {
                    required: true
                },

            },
            messages: {
            	triggerAssetId: "Please Select a Asset",
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
    
    var minDate = function() {
		var startDate = $('#ttStartDate').val();	 
		var endDate = $('#ttEndDate').val();
		if (startDate != "" && endDate != "") {
			var sDate = moment(startDate, 'DD-MM-YYYY');
			var eDate = moment(endDate, 'DD-MM-YYYY'); 			
			if ( sDate >= eDate ) {
				return false;
			}
		} 		
		return true;
	};

    /*********************************************************************
     * Init Custome Rules
     *********************************************************************/    
    
    var addValidateRules = function () {

    	var triggerType = $("input:radio[name ='smTriggerType']:checked").val();  
    	
        if ( triggerType == 'METER_READING_TRIGGER' ) {
        	addMeterReadingTriggerRules();
        } 
        else if( triggerType == 'TIME_TRIGGER') { 
        	addTimeTriggerRules(); 
        } 
        else if( triggerType == 'ABC_METER_READING_TRIGGER') { 
        	addABCMeterReadingTriggerRules(); 
        } 
        
        else { 
            setRules(triggerType); 
        }

    };  
    
    var addMeterReadingTriggerRules = function() {
    	
    	var mrtType = $("input:radio[name ='mrtType']:checked").val();  
    	
    	if ( mrtType == 'EVERY' ) {		
    		var mrtNoEndReading = $('#mrtNoEndReading').iCheck('update')[0].checked;
    		
    		if (!mrtNoEndReading) {
    			setInputRule('mrtEndMeterReading', 'Please Specify a End Meter Reading Value');
    		}
    		setRules(mrtType);
    		
    	} else if ( mrtType == 'WHEN' ){				
    		setRules(mrtType);
    	}
    	
    };
    var addABCMeterReadingTriggerRules = function() {
    	
    		var mrtNoEndReading = $('#mrtNoEndReading1').iCheck('update')[0].checked;

	        if (!mrtNoEndReading) {
	            setInputRule('mrtEndMeterReading1', 'Please Specify a End Meter Reading Value');
	        }
	       setRules("ABC");
    	        
    	
    };
    
    var addTimeTriggerRules = function() {
    	
    	var occurenceType = $("input:radio[name ='ttOccurenceType']:checked").val();  
    	
    	var ttNoEndDate = $('#ttNoEndDate').iCheck('update')[0].checked;

        if (!ttNoEndDate) { 
        	
       	 	$('#ttEndDate').rules( "add", {
                minDate: true,
                required:true,
                messages: {
                	required: 'Please Specify a End Date',
                	minDate: "End Date should be after Start Date",
                }
            });
        }
        
        setInputRule('ttStartDate', 'Please Specify a Start Date'); 
    	setRules(occurenceType);   
    	 
    }; 

    var setRules = function (type) {

        removeCustomeRules();

        switch (type) {
            case 'HOURLY':
                setInputRule('ttHourlyEveryValue', 'Please Specify a Hours');
                break;

            case 'DAILY':
                setInputRule('ttDailyEveryValue', 'Please Specify a Days');
                break;

            case 'WEEKLY':
                setCustomMinLengthRule();
                setInputRule('ttWeeklyEveryValue', 'Please Specify a Weeks');
                break;

            case 'MONTHLY':
                setInputRule('ttMonthlyEveryValue', 'Please Specify No of Months');
                setInputRule('ttMonthlyDayOfMonth', 'Please Specify a Day');
                break;

            case 'YEARLY':
                setInputRule('ttYearlyEveryValue', 'Please Specify a Years');
                setInputRule('ttYearlyMonth', 'Please Specify a Month');
                setInputRule('ttYearlyDayOfMonth', 'Please Specify a Day');
                break; 
            	
            case 'EVENT_TRIGGER':
            	setInputRule('etAssetEventTypeAssetId', 'Please Specify a Asset Event'); 
            	break;
            	
            case 'EVERY':
            	setInputRule('mrtAssetMeterReadingId', 'Please Specify a Asset Meter Reading');
            	setInputRule('mrtEveryValue', 'Please Specify a Meter Reading Value');
            	setInputRule('mrtStartMeterReading', 'Please Specify a Start Meter Reading Value');
            	break;
            case 'ABC':
            	setInputRule('mrtAssetMeterReadingId2', 'Please Specify a Asset Meter Reading');
            	setInputRule('abcTypes', 'Please Specify a ABC Type');
            	setInputRule('mrtStartMeterReading1', 'Please Specify a Start Meter Reading Value');
            	break;
            	
            case 'WHEN':
            	setInputRule('mrtAssetMeterReadingId', 'Please Specify a Asset Meter Reading');
            	setInputRule('mrtLogicType', 'Please Specify a Condition Type'); 
            	setInputRule('mrtConditionValue', 'Please Specify a Condition Value');  
                break;
        }
    };

    var setCustomMinLengthRule = function (input, message) {
        var boxes = [];
        $('#ttWeekDays').rules("remove");

        var searchIDs = $("#divWeekly input:checkbox:checked").map(function () {
            return $(this).val();
        }).get();

        if (searchIDs.length < 1) {
            $('#ttWeekDays').rules("add", {
                required: true,
                messages: {
                    required: 'Please select a at least 1 day on week',
                }
            });
        }
    };

    var setInputRule = function (input, message) {
        $('#' + input).rules("add", {
            required: true,
            messages: {
                required: message,
            }
        });
    };

    var removeCustomeRules = function () {
        $('#ttHourlyEveryValue').rules("remove");
        $('#ttDailyEveryValue').rules("remove");
        $('#ttWeeklyEveryValue').rules("remove");
        $('#ttMonthlyEveryValue').rules("remove");
        $('#ttMonthlyDayOfMonth').rules("remove");
        $('#ttYearlyEveryValue').rules("remove");
        $('#ttYearlyMonth').rules("remove");
        $('#ttYearlyDayOfMonth').rules("remove");
        $('#mrtAssetMeterReadingId').rules("remove");
        $('#mrtEveryValue').rules("remove");
        $('#ttWeekDays').rules("remove");
//        $('#ttStartDate').rules("remove");
//        $('#ttEndDate').rules("remove");
    };
    
    var initSchedulingRadio = function () {
        Main.runCheckBoxStyle();
    };

    var resetDiv = function (divId) {
        jQuery(divId).find(':input').each(function () {
            switch (this.type) {
                case 'password':
                case 'text':
                case 'number':
                case 'textarea':
                case 'file':
                case 'select':
                    jQuery(this).val('');
                    break;
                case 'checkbox':
                	jQuery(this).iCheck('uncheck');
                    break;
            }
        });
    };

    var hideDiv = function (btnId, divId) {
        $(btnId).iCheck('uncheck');
        $(divId).hide();
        resetDiv(divId);
    };

    var showDiv = function (btnId, divId) {
        $(btnId).iCheck('check');
        $(divId).fadeIn("slow");
        resetDiv(divId);
    };

    var initSchedulingView = function () {

        hideDiv('#byMeterReadingTrigger', "#divByMeterReadingTrigger");
        hideDiv('#byEventTrigger', "#divByEventTrigger");
        showDiv('#byTimeScheduleTrigger', "#divByTimeSchedule");
        hideDiv('#dailyRadioBtn', "#divDaily");
        hideDiv('#weeklyRadioBtn', "#divWeekly");
        hideDiv('#monthlyRadioBtn', "#divMonthly");
        hideDiv('#yearlyRadioBtn', "#divYearly");
        showDiv('#hourlyRadioBtn', "#divHourly");
    	
        $('#byTimeScheduleTrigger').on('ifChecked', function () {
            hideDiv('#byMeterReadingTrigger', "#divByMeterReadingTrigger");
            hideDiv('#byEventTrigger', "#divByEventTrigger");
            hideDiv('#byABCTrigger', "#divabcTrigger");
            showDiv('#byTimeScheduleTrigger', "#divByTimeSchedule");
            showDiv('#hourlyRadioBtn', "#divHourly");
            $('#ttScheduleIsFixed').iCheck('check');
        });

        $('#byMeterReadingTrigger').on('ifChecked', function () {
            hideDiv('#byTimeScheduleTrigger', "#divByTimeSchedule");
            hideDiv('#byEventTrigger', "#divByEventTrigger");
            hideDiv('#byABCTrigger', "#divabcTrigger");
            showDiv('#byMeterReadingTrigger', "#divByMeterReadingTrigger");
            showDiv('#everyRadioBtn', "#divEvery");
            $('#mrtIsFixed').iCheck('check');
        });

        $('#byEventTrigger').on('ifChecked', function () {
        	hideDiv('#byTimeScheduleTrigger', "#divByTimeSchedule");
        	hideDiv('#byMeterReadingTrigger', "#divByMeterReadingTrigger");
        	hideDiv('#byABCTrigger', "#divabcTrigger");
        	showDiv('#byEventTrigger', "#divByEventTrigger");
        	$('#inputbyTimeSchedule').iCheck('uncheck');
        });
        $('#byABCTrigger').on('ifChecked', function () {
            hideDiv('#byTimeScheduleTrigger', "#divByTimeSchedule");
            hideDiv('#byMeterReadingTrigger', "#divByMeterReadingTrigger");
            hideDiv('#byEventTrigger', "#divByEventTrigger");
            showDiv('#byABCTrigger', "#divabcTrigger");
            $('#inputbyTimeSchedule').iCheck('uncheck');
        });

        $('#hourlyRadioBtn').on('ifChecked', function () {
            hideDiv('#dailyRadioBtn', "#divDaily");
            hideDiv('#weeklyRadioBtn', "#divWeekly");
            hideDiv('#monthlyRadioBtn', "#divMonthly");
            hideDiv('#yearlyRadioBtn', "#divYearly");
            showDiv('#hourlyRadioBtn', "#divHourly");
            $('#ttScheduleIsFixed').iCheck('check');
        });

        $('#dailyRadioBtn').on('ifChecked', function () {
            hideDiv('#hourlyRadioBtn', "#divHourly");
            hideDiv('#weeklyRadioBtn', "#divWeekly");
            hideDiv('#monthlyRadioBtn', "#divMonthly");
            hideDiv('#yearlyRadioBtn', "#divYearly");
            showDiv('#dailyRadioBtn', "#divDaily");
            $('#ttScheduleIsFixed').iCheck('check');
        });

        $('#weeklyRadioBtn').on('ifChecked', function () {
            hideDiv('#hourlyRadioBtn', "#divHourly");
            hideDiv('#dailyRadioBtn', "#divDaily");
            hideDiv('#monthlyRadioBtn', "#divMonthly");
            hideDiv('#yearlyRadioBtn', "#divYearly");
            showDiv('#weeklyRadioBtn', "#divWeekly");
            $('#ttScheduleIsFixed').iCheck('check');
        });

        $('#monthlyRadioBtn').on('ifChecked', function () {
            hideDiv('#hourlyRadioBtn', "#divHourly");
            hideDiv('#dailyRadioBtn', "#divDaily");
            hideDiv('#weeklyRadioBtn', "#divWeekly");
            hideDiv('#yearlyRadioBtn', "#divYearly");
            showDiv('#monthlyRadioBtn', "#divMonthly");
            $('#ttScheduleIsFixed').iCheck('check');
        });

        $('#yearlyRadioBtn').on('ifChecked', function () {
            hideDiv('#hourlyRadioBtn', "#divHourly");
            hideDiv('#dailyRadioBtn', "#divDaily");
            hideDiv('#weeklyRadioBtn', "#divWeekly");
            hideDiv('#monthlyRadioBtn', "#divMonthly");
            showDiv('#yearlyRadioBtn', "#divYearly");
            $('#ttScheduleIsFixed').iCheck('check');
        });

        $('#everyRadioBtn').on('ifChecked', function () {
            hideDiv('#whenRadioBtn', "#divWhen");
            showDiv('#everyRadioBtn', "#divEvery");
        });

        $('#whenRadioBtn').on('ifChecked', function () {
            hideDiv('#everyRadioBtn', "#divEvery");
            showDiv('#whenRadioBtn', "#divWhen");
        });

        /* No ending date */
        $('#ttNoEndDate').on('ifChecked', function () {
            $("#ttEndDate").prop('disabled', true);
        });

        $('#ttNoEndDate').on('ifUnchecked', function () {
            $("#ttEndDate").prop('disabled', false);
        });

        /* No Meter Reading */
        $('#mrtNoEndReading').on('ifChecked', function () {
        	$("#mrtEndMeterReading").prop('disabled', true);
        });
        $('#mrtNoEndReading1').on('ifChecked', function () {
            $("#mrtEndMeterReading1").prop('disabled', true);
        });

        $('#mrtNoEndReading').on('ifUnchecked', function () {
        	$("#mrtEndMeterReading").prop('disabled', false);
        });
        $('#mrtNoEndReading1').on('ifUnchecked', function () {
            $("#mrtEndMeterReading1").prop('disabled', false);
        });

    };

    return {
        init: function () {
            initYearlyMonthSelect();
            initAssetMeterReadingSelect();
            initMeterReadingLogicSelect();
            initAssetMeterReadingSelect2();
            initMeterReadingABCTypeSelect();
            initAssetEventTypeAssetSelect();
            initDatePicker();
            initTriggerAssetSelect();
            initSchedulingRadio();
            initSchedulingView();
            initABCTypeSelect();
            initButtons();
            initDivs();
            initValidator();
        },
        
        addTrigger: function() {
        	addTrigger();
		}
    };
}();