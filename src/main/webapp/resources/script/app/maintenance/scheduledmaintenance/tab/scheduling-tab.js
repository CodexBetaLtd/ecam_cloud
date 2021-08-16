var TabScheduling = function () {

    var initButtons = function () {

        $('#new-trigger-btn').on('click', function () {
            TabScheduling.triggerAddModal();
        });
        
        $('#manual-trigger-btn').on('click', function () {
            TabScheduling.manualTrigger();
        });

    };

    var triggerAddModal = function () {
        var $modal = $('#trigger-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/triggermodalview';
            $modal.load(url, '', function () {
                TriggerAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var addTriggerToList = function (trigger) {

        var triggerObj = {};

        initVariables(triggerObj);

        CustomValidation.validateFieldNull(triggerObj, 'id', trigger.id);
        CustomValidation.validateFieldNull(triggerObj, 'index', trigger.index);
        CustomValidation.validateFieldNull(triggerObj, 'version', trigger.version);
        CustomValidation.validateFieldNull(triggerObj, 'assetId', trigger.assetId);
        CustomValidation.validateFieldNull(triggerObj, 'assetName', trigger.assetName);
        CustomValidation.validateFieldNull(triggerObj, 'triggerType', trigger.triggerType);
        CustomValidation.validateFieldNull(triggerObj, 'scheduledMaintenanceId', trigger.scheduledMaintenanceId);
        CustomValidation.validateFieldNull(triggerObj, 'everyValue', trigger.everyValue);
        CustomValidation.validateFieldNull(triggerObj, 'scheduleIsFixed', trigger.scheduleIsFixed);
        CustomValidation.validateFieldNull(triggerObj, 'lastTriggeredDate', trigger.lastTriggeredDate);
        CustomValidation.validateFieldNull(triggerObj, 'scheduleDescription', trigger.scheduleDescription);
        CustomValidation.validateFieldNull(triggerObj, 'ttOccurenceType', trigger.ttOccurenceType);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInSunday', trigger.ttWeeklyInSunday);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInMonday', trigger.ttWeeklyInMonday);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInTuesday', trigger.ttWeeklyInTuesday);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInWednesday', trigger.ttWeeklyInWednesday);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInThursday', trigger.ttWeeklyInThursday);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInFriday', trigger.ttWeeklyInFriday);
        CustomValidation.validateFieldNull(triggerObj, 'ttWeeklyInSaturday', trigger.ttWeeklyInSaturday);
        CustomValidation.validateFieldNull(triggerObj, 'ttDayOfMonth', trigger.ttDayOfMonth);
        CustomValidation.validateFieldNull(triggerObj, 'ttMonth', trigger.ttMonth);
        CustomValidation.validateFieldNull(triggerObj, 'ttMonthName', trigger.ttMonthName);
        CustomValidation.validateFieldNull(triggerObj, 'ttEndDate', trigger.ttEndDate);
        CustomValidation.validateFieldNull(triggerObj, 'ttStartDate', trigger.ttStartDate);
        CustomValidation.validateFieldNull(triggerObj, 'ttCreateWOOnStartDate', trigger.ttCreateWOOnStartDate);
        CustomValidation.validateFieldNull(triggerObj, 'ttNoEndDate', trigger.ttNoEndDate);
        CustomValidation.validateFieldNull(triggerObj, 'mrtType', trigger.mrtType);
        CustomValidation.validateFieldNull(triggerObj, 'mrtAssetMeterReadingId', trigger.mrtAssetMeterReadingId);
        CustomValidation.validateFieldNull(triggerObj, 'mrtEveryValue', trigger.mrtEveryValue);
        CustomValidation.validateFieldNull(triggerObj, 'mrtLogicType', trigger.mrtLogicType);
        CustomValidation.validateFieldNull(triggerObj, 'mrtLogicTypeName', trigger.mrtLogicTypeName);
        CustomValidation.validateFieldNull(triggerObj, 'mrtStartMeterReading', trigger.mrtStartMeterReading);
        CustomValidation.validateFieldNull(triggerObj, 'mrtEndMeterReading', trigger.mrtEndMeterReading);
        CustomValidation.validateFieldNull(triggerObj, 'mrtNoEndReading', trigger.mrtNoEndReading);
        CustomValidation.validateFieldNull(triggerObj, 'mrtConditionValue', trigger.mrtConditionValue);
        CustomValidation.validateFieldNull(triggerObj, 'etAssetEventTypeAssetId', trigger.etAssetEventTypeAssetId);
        CustomValidation.validateFieldNull(triggerObj, 'etAssetEventTypeAssetName', trigger.etAssetEventTypeAssetName);
        CustomValidation.validateFieldNull(triggerObj, 'smabcTriggerType', trigger.smabcTriggerType);

        createSummary(triggerObj);

        triggers.push(triggerObj);
    };

    var initTriggerHtmlTable = function () {

        if (triggers.length > 0) {
            var row, trigger;
            $("#sm-trigger-tbl > tbody").html("");

            for (row = 0; row < triggers.length; row++) {
                trigger = triggers[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='triggers" + row + ".id' name='triggers[" + row + "].id' value='" + CustomValidation.nullValueReplace(trigger.id) + "' type='hidden'>" +
                    "<input id='triggers" + row + ".index' name='triggers[" + row + "].index' value='" + CustomValidation.nullValueReplace(trigger.index) + "' type='hidden'>" +
                    "<input id='triggers" + row + ".version' name='triggers[" + row + "].version' value='" + CustomValidation.nullValueReplace(trigger.version) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".assetId' name='triggers[" + row + "].assetId' value='" + CustomValidation.nullValueReplace(trigger.assetId) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".assetName' name='triggers[" + row + "].assetName' value='" + CustomValidation.nullValueReplace(trigger.assetName) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".triggerType' name='triggers[" + row + "].triggerType' value='" + CustomValidation.nullValueReplace(trigger.triggerType) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".scheduledMaintenanceId' name='triggers[" + row + "].scheduledMaintenanceId' value='" + CustomValidation.nullValueReplace(trigger.scheduledMaintenanceId) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".everyValue' name='triggers[" + row + "].everyValue' value='" + CustomValidation.nullValueReplace(trigger.everyValue) + "' type='hidden' >" +
//                    "<input id='triggers" + row + ".scheduleIsFixed' name='triggers[" + row + "].scheduleIsFixed' value='" + CustomValidation.nullValueReplace(trigger.scheduleIsFixed) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".lastTriggeredDate' name='triggers[" + row + "].lastTriggeredDate' value='" + CustomValidation.nullValueReplace(trigger.lastTriggeredDate) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".scheduleDescription' name='triggers[" + row + "].scheduleDescription' value='" + CustomValidation.nullValueReplace(trigger.scheduleDescription) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttOccurenceType' name='triggers[" + row + "].ttOccurenceType' value='" + CustomValidation.nullValueReplace(trigger.ttOccurenceType) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInSunday' name='triggers[" + row + "].ttWeeklyInSunday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInSunday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInMonday' name='triggers[" + row + "].ttWeeklyInMonday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInMonday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInTuesday' name='triggers[" + row + "].ttWeeklyInTuesday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInTuesday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInWednesday' name='triggers[" + row + "].ttWeeklyInWednesday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInWednesday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInThursday' name='triggers[" + row + "].ttWeeklyInThursday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInThursday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInFriday' name='triggers[" + row + "].ttWeeklyInFriday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInFriday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttWeeklyInSaturday' name='triggers[" + row + "].ttWeeklyInSaturday' value='" + CustomValidation.nullValueReplace(trigger.ttWeeklyInSaturday) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttDayOfMonth' name='triggers[" + row + "].ttDayOfMonth' value='" + CustomValidation.nullValueReplace(trigger.ttDayOfMonth) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttMonth' name='triggers[" + row + "].ttMonth' value='" + CustomValidation.nullValueReplace(trigger.ttMonth) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttEndDate' name='triggers[" + row + "].ttEndDate' value='" + CustomValidation.nullValueReplace(trigger.ttEndDate) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttStartDate' name='triggers[" + row + "].ttStartDate' value='" + CustomValidation.nullValueReplace(trigger.ttStartDate) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttCreateWOOnStartDate' name='triggers[" + row + "].ttCreateWOOnStartDate' value='" + CustomValidation.nullValueReplace(trigger.ttCreateWOOnStartDate) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".ttNoEndDate' name='triggers[" + row + "].ttNoEndDate' value='" + CustomValidation.nullValueReplace(trigger.ttNoEndDate) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtType' name='triggers[" + row + "].mrtType' value='" + CustomValidation.nullValueReplace(trigger.mrtType) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtAssetMeterReadingId' name='triggers[" + row + "].mrtAssetMeterReadingId' value='" + CustomValidation.nullValueReplace(trigger.mrtAssetMeterReadingId) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtLogicType' name='triggers[" + row + "].mrtLogicType' value='" + CustomValidation.nullValueReplace(trigger.mrtLogicType) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtLogicTypeName' name='triggers[" + row + "].mrtLogicTypeName' value='" + CustomValidation.nullValueReplace(trigger.mrtLogicTypeName) + "' type='hidden' >" +
//                    "<input id='triggers" + row + ".mrtNextMeterReading' name='triggers[" + row + "].mrtNextMeterReading' value='" + CustomValidation.nullValueReplace( trigger.mrtNextMeterReading ) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtStartMeterReading' name='triggers[" + row + "].mrtStartMeterReading' value='" + CustomValidation.nullValueReplace(trigger.mrtStartMeterReading) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtEndMeterReading' name='triggers[" + row + "].mrtEndMeterReading' value='" + CustomValidation.nullValueReplace(trigger.mrtEndMeterReading) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtNoEndReading' name='triggers[" + row + "].mrtNoEndReading' value='" + CustomValidation.nullValueReplace(trigger.mrtNoEndReading) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtConditionValue' name='triggers[" + row + "].mrtConditionValue' value='" + CustomValidation.nullValueReplace(trigger.mrtConditionValue) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".mrtEveryValue' name='triggers[" + row + "].mrtEveryValue' value='" + CustomValidation.nullValueReplace(trigger.mrtEveryValue) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".etAssetEventTypeAssetId' name='triggers[" + row + "].etAssetEventTypeAssetId' value='" + CustomValidation.nullValueReplace(trigger.etAssetEventTypeAssetId) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".etAssetEventTypeAssetName' name='triggers[" + row + "].etAssetEventTypeAssetName' value='" + CustomValidation.nullValueReplace(trigger.etAssetEventTypeAssetName) + "' type='hidden' >" +
                    "<input id='triggers" + row + ".smabcTriggerType' name='triggers[" + row + "].smabcTriggerType' value='" + CustomValidation.nullValueReplace(trigger.smabcTriggerType) + "' type='hidden' >" +
                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input type='checkbox' name='selectedTriggers' value='" + trigger.id + "' class='grey'>" +
                    "</div>" +
                    "</td>" +
                    "<td><span>" + trigger.assetName + "</span></td>" +
                    "<td class='hidden-xs'><span>" + createSummary(trigger) + "</span></td>" +
                    "<td><span>" + CustomValidation.nullValueReplace(trigger.nextTrigger) + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("TabScheduling.removeTrigger", trigger.index) +                    
                    "</div></td></tr>";
                
                $('#sm-trigger-tbl > tbody:last-child').append(html);
            }
            
            $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
                checkboxClass: 'icheckbox_minimal-grey',
                radioClass: 'iradio_minimal-grey',
                increaseArea: '10%'
            });
            
        } else {
            $("#sm-trigger-tbl > tbody").html("<tr><td colspan='4' align='center'>Please Add Triggers for the Scheduled Maintenance.</td></tr>");
        }
    };

    var initVariables = function (trigger) {
        trigger['id'] = "";
        trigger['index'] = "";
        trigger['version'] = "";
        trigger['assetId'] = "";
        trigger['assetName'] = "";
        trigger['triggerType'] = "";
        trigger['scheduledMaintenanceId'] = "";
        trigger['everyValue'] = "";
        trigger['scheduleIsFixed'] = "";
        trigger['lastTriggeredDate'] = "";
        trigger['scheduleDescription'] = "";
        trigger['ttOccurenceType'] = "";
        trigger['ttWeeklyInSunday'] = false;
        trigger['ttWeeklyInMonday'] = false;
        trigger['ttWeeklyInTuesday'] = false;
        trigger['ttWeeklyInWednesday'] = false;
        trigger['ttWeeklyInThursday'] = false;
        trigger['ttWeeklyInFriday'] = false;
        trigger['ttWeeklyInSaturday'] = false;
        trigger['ttDayOfMonth'] = "";
        trigger['ttMonth'] = "";
        trigger['ttMonthName'] = "";
        trigger['ttEndDate'] = "";
        trigger['ttStartDate'] = "";
        trigger['ttCreateWOOnStartDate'] = false;
        trigger['ttNoEndDate'] = false;
        trigger['mrtType'] = "";
        trigger['mrtAssetMeterReadingId'] = "";
        trigger['mrtEveryValue'] = "";
        trigger['mrtLogicType'] = "";
        trigger['mrtLogicTypeName'] = "";
        trigger['mrtStartMeterReading'] = "";
        trigger['mrtEndMeterReading'] = "";
        trigger['mrtNoEndReading'] = false;
        trigger['mrtConditionValue'] = "";
        trigger['etAssetEventTypeAssetId'] = "";
        trigger['etAssetEventTypeAssetName'] = "";
        trigger['smabcTriggerType'] = "";
    };

    var addTrigger = function () {

        var triggerType = $('input[name=smTriggerType]:checked').val();

        var trigger = {};

        trigger['id'] = $('#triggerId').val();
        trigger['version'] = $('#triggerVersion').val();
        trigger['index'] = getCurrentTriggerIndex();
        trigger['assetId'] = $('#triggerAssetId').val();
        trigger['assetName'] = $('#triggerAssetId').select2('data')[0].text;
        trigger['scheduledMaintenanceId'] = $('#scheduledMaintenanceId').val();
        trigger['lastTriggeredDate'] = $('#lastTriggeredDate').val();

        var ttOccurenceType = $('input[name=ttOccurenceType]:checked').val();
        trigger['ttOccurenceType'] = ttOccurenceType;
        trigger['triggerType'] = triggerType;
        trigger['scheduleDescription'] = $('#scheduleDescription').val();
        trigger['ttWeeklyInSunday'] = $('input[id=ttWeeklyInSunday]:checked').val();
        trigger['ttWeeklyInMonday'] = $('input[id=ttWeeklyInMonday]:checked').val();
        trigger['ttWeeklyInTuesday'] = $('input[id=ttWeeklyInTuesday]:checked').val();
        trigger['ttWeeklyInWednesday'] = $('input[id=ttWeeklyInWednesday]:checked').val();
        trigger['ttWeeklyInThursday'] = $('input[id=ttWeeklyInThursday]:checked').val();
        trigger['ttWeeklyInFriday'] = $('input[id=ttWeeklyInFriday]:checked').val();
        trigger['ttWeeklyInSaturday'] = $('input[id=ttWeeklyInSaturday]:checked').val();
        trigger['ttCreateWOOnStartDate'] = $('input[id=ttCreateWorkOrderOnStart]:checked').val();
        trigger['ttEndDate'] = $('#ttEndDate').val();
        trigger['ttStartDate'] = $('#ttStartDate').val();
        trigger['ttNoEndDate'] = $('input[id=ttNoEndDate]:checked').val();

        trigger['mrtType'] = $('input[name=mrtType]:checked').val();
        trigger['mrtAssetMeterReadingId'] = $('#mrtAssetMeterReadingId').val();
        trigger['mrtLogicType'] = $('#mrtLogicType').val();
        trigger['mrtLogicTypeName'] = $('#mrtLogicType').select2('data')[0].text;
        trigger['mrtNextMeterReading'] = $('#mrtNextMeterReading').val();
        if($('#mrtAssetMeterReadingId').val()==""){
            trigger['mrtAssetMeterReadingId'] = $('#mrtAssetMeterReadingId2').val();
        }
        trigger['mrtStartMeterReading'] = $('#mrtStartMeterReading').val();
        if($('#mrtStartMeterReading').val()==""){
            trigger['mrtStartMeterReading'] = $('#mrtStartMeterReading1').val();
        }
        trigger['mrtEndMeterReading'] = $('#mrtEndMeterReading').val();
        if($('#mrtEndMeterReading').val()==""){
            trigger['mrtEndMeterReading'] = $('#mrtEndMeterReading1').val();
        }
        trigger['mrtNoEndReading'] = $('input[id=mrtNoEndReading]:checked').val();
        if($('input[id=mrtNoEndReading]:checked').val()){
            trigger['mrtNoEndReading'] = $('input[id=mrtNoEndReading1]:checked').val();
        }
        trigger['mrtConditionValue'] = $('#mrtConditionValue').val();
        trigger['mrtEveryValue'] = $('#mrtEveryValue').val();
        if($('#mrtEveryValue').val()==""){
            trigger['mrtEveryValue'] = $('#mrtEveryValue1').val();
        }
        trigger['smabcTriggerType'] = $('#abcTypes').val();

        if (triggerType == 'TIME_TRIGGER') {

            trigger['scheduleIsFixed'] = $('input[name=ttScheduleIsFixed]:checked').val();

            if (ttOccurenceType == "HOURLY") {

                trigger['everyValue'] = $('#ttHourlyEveryValue').val();

            } else if (ttOccurenceType == "DAILY") {

                trigger['everyValue'] = $('#ttDailyEveryValue').val();

            } else if (ttOccurenceType == "WEEKLY") {

                trigger['everyValue'] = $('#ttWeeklyEveryValue').val();

            } else if (ttOccurenceType == "MONTHLY") {

                trigger['everyValue'] = $('#ttMonthlyEveryValue').val();
                trigger['ttDayOfMonth'] = $('#ttMonthlyDayOfMonth').val();

            } else if (ttOccurenceType == "YEARLY") {

                trigger['everyValue'] = $('#ttYearlyEveryValue').val();
                trigger['ttMonth'] = $('#ttYearlyMonth').val();
                trigger['ttMonthName'] = $('#ttYearlyMonth').select2('data')[0].text;
                trigger['ttDayOfMonth'] = $('#ttYearlyDayOfMonth').val();
            }

        } else if (triggerType == 'METER_READING_TRIGGER') {

            trigger['everyValue'] = $('#mrtEveryValue').val();
            trigger['scheduleIsFixed'] = $('input[name=mrtIsFixed]:checked').val();

        } else if (triggerType == 'EVENT_TRIGGER') {

            trigger['etAssetEventTypeAssetId'] = $('#etAssetEventTypeAssetId').val();
            trigger['etAssetEventTypeAssetName'] = $('#etAssetEventTypeAssetId').select2('data')[0].text;

        }
console.log(trigger)
        addTriggerToList(trigger);
        initTriggerHtmlTable();

        $('#trigger-modal').modal('toggle');
    };
    
    var getCurrentTriggerIndex = function () {
    	
    	var currentIndex = 0;
    	
    	$.each(triggers, function (index, trigger) {    		
    		if (trigger.index >= currentIndex) {
    			currentIndex = ( trigger.index + 1 );
    		}
        });
    	
    	return currentIndex;
    };

    var removeTrigger = function (index) {
    	
    	if (!isTriggerUsed(index)) {
    		$.each(triggers, function (i, trigger) {    		
        		if (trigger.index == index) {
        			triggers.splice(i, 1);
        			return false;
        		}
            });            
            initTriggerHtmlTable();
    	}    	
    	
    };
    
    var getTriggerByIndex = function (index) { 
    	for (var i = 0; i < triggers.length; i++ ) {
    		if ( triggers[i].index == index ) {
    			return triggers[i];
    		}
    	}     
    };
    
    var manualTrigger = function () {
    	
    	var isSaved = true;
    	
    	var checkedValues = $("input[name='selectedTriggers']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    	    return this.value;
    	}).get();
    	
    	if (isSaved) {
    		if (checkedValues != null && checkedValues != "") {     			
    			$.ajax({
                    type: "GET",
                    url: "../scheduledmaintenance/manual-trigger?ids=" + checkedValues + "&smId=" + $('#smId').val(),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (result) {
                    	if (result.status == "SUCCESS") {
                    		var mappingUrlText = " " + result.msgList[1] + " ID = ( " + result.msgList[2] + " )";
                    		var mappingUrl = '../workorder/edit?id=' + result.msgList[2]; 
                    		$("#message-div").html(CustomComponents.getSuccessMsgDivWithUrl(result.msgList[0], mappingUrlText, mappingUrl));
                    	} else {
                    		$("#message-div").html(CustomComponents.getErrorMsgDiv(result.errorList[0]));
                    	}
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status + " " + thrownError);
                    },
                    error: function (e) {
                        alert("Failed to Create Work Order.");
                        console.log(e);
                    }
                });	    
        	} else {
        		alert("Please select atleast one trigger");
        	}
    	} else {
    		alert("There are some unsaved triggers. Please save them before trigger");
    	}    	
    };
    
    var isTriggerUsed = function (triggerIndex) {
    	var isTriggerUsed = false;
    	
    	if ( isTriggerUsedInTasks(triggerIndex) ) {
    		isTriggerUsed = true;
    		alert("Trigger Already Used in the Scheduled Tasks. Please Remove Them First..!!");
    	} 
    	
    	return isTriggerUsed;
    };
    
    var isTriggerUsedInTasks = function (triggerIndex) {
    	
    	var isTriggerUsedInTasks = false;
    	
    	$.each(scheduledTasks, function (index, task) {
    		if ( task.triggerIndex == triggerIndex ) {
    			isTriggerUsedInTasks = true;
    			return false
    		}
    	});
    	
    	return isTriggerUsedInTasks;
    };

    var createSummary = function (trigger) {

        var summary = "";

        if (trigger.triggerType == "TIME_TRIGGER") {
        	
            summary = "Time Trigger";

            if (trigger.ttOccurenceType == "HOURLY") {

                summary += " - Every " + trigger.everyValue + " Hour(s)";

            } else if (trigger.ttOccurenceType == "DAILY") {

                summary += " - Every " + trigger.everyValue + " Day(s)";

            } else if (trigger.ttOccurenceType == "WEEKLY") {

                summary += " - Every " + trigger.everyValue + " Week(s) on ";
                
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInSunday)) {
                    summary += "Sunday ";
                }
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInMonday)) {
                    summary += "Monday ";
                }
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInTuesday)) {
                    summary += "Tuesday ";
                }
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInWednesday)) {
                    summary += "Wednesday ";
                }
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInThursday)) {
                    summary += "Thursday ";
                }
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInFriday)) {
                    summary += "Friday ";
                }
                if (CustomValidation.isBooleanTrue(trigger.ttWeeklyInSaturday)) {
                    summary += "Saturday ";
                }

            } else if (trigger.ttOccurenceType == "MONTHLY") {

                summary += " - Every " + trigger.everyValue + " Month(s) on " + trigger.ttDayOfMonth + " day";

            } else if (trigger.ttOccurenceType == "YEARLY") {

                summary += " - Every " + trigger.everyValue + " Year(s) on " + trigger.ttDayOfMonth + " th of " + trigger.ttMonthName;
            }
        } else if (trigger.triggerType == "METER_READING_TRIGGER") {

            summary = "Meter Reading Trigger";

            if (trigger.mrtType == "EVERY") {

                summary += " - Every " + trigger.everyValue;

            } else if (trigger.mrtType == "WHEN") {

                summary += " - When meter reading " + trigger.mrtLogicTypeName + " " + trigger.everyValue;

            }

        } else if (trigger.triggerType == "EVENT_TRIGGER") {
        	
        	summary = "Event Trigger - When Occur " + trigger.etAssetEventTypeAssetName;
        	
        }
         else if (trigger.triggerType == "ABC_METER_READING_TRIGGER") {

           summary = "ABC Meter Reading Trigger";

            //if (trigger.mrtType == "EVERY") {

                summary += " - " + trigger.smabcTriggerType;
            

//            } else if (trigger.mrtType == "WHEN") {
//
//                summary += " - When meter reading " + trigger.mrtLogicTypeName + " " + trigger.everyValue;
//
//            }

        }

        return summary;
    };

    return {

        init: function () {
            initButtons();
            initTriggerHtmlTable();
        },
    	
        addTrigger: function () {
            addTrigger();
        },

        removeTrigger: function (index) {
            removeTrigger(index);
        },
        
        manualTrigger: function () {
        	manualTrigger();
        },

        resetTriggerHtmlTable: function () {
            resetTriggerHtmlTable();
        },

        addTriggerToList: function (trigger) {
            addTriggerToList(trigger);
        },

        triggerAddModal: function () {
            triggerAddModal();
        },
        
        createSummary: function (trigger) {
        	return createSummary(trigger);
        }
    };

}();