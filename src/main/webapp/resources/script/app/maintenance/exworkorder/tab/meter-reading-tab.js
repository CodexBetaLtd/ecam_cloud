
/*********************************************************************
 * Work Order Meter Reading
 *********************************************************************/
var MeterReadingTab = function () {
	
	var initButtons = function () {
		
		$('#new-meter-reading-btn').on('click', function () {
			MeterReadingTab.meterReadingAddView();
        });	
		
	};
    
    var getSelectedAssetMeterReadingValue = function (meterReadingId) {
        $.ajax({
            type: "GET",
            url: '../restapi/asset/getMeterReadingById?id=' + meterReadingId,
            contentType: "application/json",
            dataType: 'json',
            success: function (json) {
            	$('#woMeterReadingCurrentValue').val(json.meterReadingCurrentValue);
                $('#woMeterReadingCurrentValueId').val(json.meterReadingCurrentValueId);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            },
            error: function (e) {
                alert("Failed to load data");
            }
        });
    };

    var meterReadingAddView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/meter-reading-add-modal-view';
            $modal.load(url, '', function () {
            	MeterReadingAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var meterReadingEditView = function (index) {
        var $modal = $('#master-modal-datatable');
        var meterReading = meterReadings[index];
        var assetId = meterReading.meterReadingAssetId;        
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/meter-reading-edit-modal-view?id=' + assetId;
            $modal.load(url, '', function () {
            	MeterReadingAddModal.init();
                fillEditForm(meterReading, index);
                $modal.modal();
            });
        }, 1000);
    };
    
    var meterReadingValueAddView = function (meterReadingIndex, meterReadingId, meterReadingName) {
    	var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/meter-reading-value-add-modal-view';
            $modal.load(url, '', function () {               
                MeterReadingValueAddModal.init(meterReadingIndex, meterReadingName, meterReadingId);
                $modal.modal();
            });
        }, 1000);
    };

    var meterReadingHistoryView = function (meterReadingId) {
    	if (meterReadingId == null || meterReadingId == undefined || meterReadingId == "") {
    		alert("Please Save the workorder First.")
    	} else {
    		var $modal = $('#master-modal-datatable');
    		CustomComponents.ajaxModalLoadingProgressBar();
    		setTimeout(function () {
    			var url = '../asset/assetmeterreadinghistorymodelview';
    			$modal.load(url, '', function () {
    				MeterReadingHistoryModel.init(meterReadingId, "../restapi/assetmeterreadingvalue/historybymeterreading/" );
    				$modal.modal();
    			});
    		}, 1000);
    	}
    };
    
    var fillEditForm = function (meterReading, index) {
        $('#woMeterReadingIndex').val(index);
        $('#woMeterReadingAssignedAssetId').val(meterReading.meterReadingAssetId).trigger('change');
        $('#woMeterReadingId').val(meterReading.meterReadingId).trigger('change');
        $('#woMeterReadingDescription').val(meterReading.meterReadingDescription);
        $('#woMeterReadingCurrentValueId').val(meterReading.meterReadingCurrentValueId);
        $('#woMeterReadingCurrentValueIndex').val(meterReading.meterReadingCurrentValueIndex);
        $('#woMeterReadingCurrentValue').val(meterReading.meterReadingCurrentValue);
        $('#woMeterReadingVersion').val(meterReading.version);     
    };
    
    var addMeterReading = function () {
        if ($("#woMeterReadingIndex").val() != null && $("#woMeterReadingIndex").val() != "") {
            updateArrayObject(meterReadings[$("#woMeterReadingIndex").val()]);
            initMeterReadingTable();
            $('#master-modal-datatable').modal('toggle');
        } else {
            addArrayObject();
        }
    };

    var addArrayObject = function () {
    	 var meterReading = {};
         meterReading['id'] = "";
         meterReading['version'] = "";
         meterReading['meterReadingIndex'] = "";
         meterReading['meterReadingAssetName'] = $('#woMeterReadingAssignedAssetId :selected').text();
         meterReading['meterReadingAssetId'] = $('#woMeterReadingAssignedAssetId').val();
         meterReading['meterReadingName'] = $("#woMeterReadingId :selected").text();
         meterReading['meterReadingId'] = $("#woMeterReadingId").val();
         meterReading['meterReadingCurrentValue'] = $("#woMeterReadingCurrentValue").val();
         meterReading['meterReadingCurrentValueId'] = $("#woMeterReadingCurrentValueId").val();
         meterReading['meterReadingCurrentValueIndex'] = $('#woMeterReadingCurrentValueIndex').val();
         meterReading['meterReadingDescription'] = $("#woMeterReadingDescription").val();
         meterReading['meterReadingValueAddedDate'] = "";
         meterReading['meterReadingValueAddedDateStr'] = "";
         if (!isMeterReadingAlreadyAdd(meterReading)) {
         	woMeterReadingToList(meterReading);
         	initMeterReadingTable();
         	$('#master-modal-datatable').modal('toggle');
         } else {
             alert('Duplicate Entry');
         }    	
    };

    var updateArrayObject = function (meterReading) {   	
    	CustomValidation.validateFieldNull(meterReading,'meterReadingAssetId',$('#woMeterReadingAssignedAssetId').val());
    	CustomValidation.validateFieldNull(meterReading,'meterReadingAssetName',$("#woMeterReadingAssignedAssetId :selected").text());
    	CustomValidation.validateFieldNull(meterReading,'meterReadingId', $("#woMeterReadingId").val());
    	CustomValidation.validateFieldNull(meterReading,'meterReadingName',$("#woMeterReadingId :selected").text());
    	CustomValidation.validateFieldNull(meterReading,'meterReadingCurrentValue',$("#woMeterReadingCurrentValue").val());
    	CustomValidation.validateFieldNull(meterReading,'meterReadingDescription', $("#woMeterReadingDescription").val());
    };
    
    var woMeterReadingToList = function (woMeterReading) {
        var woMeterReadingObj = {}
        if (woMeterReading.meterReadingIndex != null && woMeterReading.meterReadingIndex != "" && woMeterReading.meterReadingIndex >= 0) {
            woMeterReadingObj = getWoMeterReadingByIndex(woMeterReading.meterReadingIndex);
            setCommonDataToMeterReading(woMeterReading, woMeterReadingObj);
        } else {
            setCommonDataToMeterReading(woMeterReading, woMeterReadingObj);
            meterReadings.push(woMeterReadingObj);
        }
    };
    
    var setCommonDataToMeterReading = function (woMeterReading, woMeterReadingObj) {
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'id', woMeterReading.id);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingId', woMeterReading.meterReadingId);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingAssetId', woMeterReading.meterReadingAssetId);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingAssetName', woMeterReading.meterReadingAssetName);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingName', woMeterReading.meterReadingName);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingCurrentValueId', woMeterReading.meterReadingCurrentValueId);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingCurrentValue', woMeterReading.meterReadingCurrentValue);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingCurrentValueIndex', woMeterReading.meterReadingCurrentValueIndex);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'meterReadingDescription', woMeterReading.meterReadingDescription);
    	CustomValidation.validateFieldNull(woMeterReadingObj, 'version', woMeterReading.version);
    };
       
    var addMeterReadingValue = function () {
        var meterReadingValue = {};
        meterReadingValue['woMeterReadingId'] = $('#meterReadingId').val();
        meterReadingValue['woMeterReadingValue'] = $('#meterReadingValue').val();
        meterReadingValue['woMeterReadingName'] = $('#meterReadingName').val();
        meterReadingValue['woMeterReadingIndex'] = $('#meterReadingIndex').val();
        meterReadingValue['woMeterReadingCurrentValueId'] = "";
        
        var time = new Date();
        meterReadingValue['woMeterReadingValueAddedDate'] = time.getTime();
        meterReadingValue['woMeterReadingValueAddedDateStr'] = moment(time).format('YYYY-MM-DD hh:mm:ss A');
        
        woMeterReadingValueToList(meterReadingValue);
        $('#master-modal-datatable').modal('toggle');
    };
    
    
    var woMeterReadingValueToList = function (meterReadingValue) {
    	setMeterReadingValueIndex(meterReadingValue);
        updateWoMeterReading(meterReadingValue);
        woMeterReadingNewValueToTable();
    };
    
    var setMeterReadingValueIndex = function (meterReadingValue){
    	if (woMeterReadingValues.length == 0) {
            meterReadingValue['woMeterReadingValueIndex'] = 0;
        } else {
        	CustomValidation.validateFieldNull(meterReadingValue, 'woMeterReadingValueIndex', woMeterReadingValues.length);
        }
    	
    	woMeterReadingValues.push(meterReadingValue);  	
    };

    var updateWoMeterReading = function (woMeterReadingValue) {
        var woMeterReading = meterReadings[woMeterReadingValue.woMeterReadingIndex];
        CustomValidation.validateFieldNull(woMeterReading, 'meterReadingName', woMeterReadingValue.woMeterReadingName);
        CustomValidation.validateFieldNull(woMeterReading, 'meterReadingCurrentValue', woMeterReadingValue.woMeterReadingValue);
        CustomValidation.validateFieldNull(woMeterReading, 'meterReadingCurrentValueIndex', woMeterReadingValue.woMeterReadingValueIndex);
        CustomValidation.validateFieldNull(woMeterReading, 'meterReadingCurrentValueId', woMeterReadingValue.woMeterReadingCurrentValueId);
        CustomValidation.validateFieldNull(woMeterReading, 'meterReadingValueAddedDate', woMeterReadingValue.woMeterReadingValueAddedDate);
        CustomValidation.validateFieldNull(woMeterReading, 'meterReadingValueAddedDateStr', woMeterReadingValue.woMeterReadingValueAddedDateStr);
        
        initMeterReadingTable();
    };
    
    var woMeterReadingNewValueToTable = function () {
        if (woMeterReadingValues.length > 0) {
            var row, meterReadingValue;
            $('#work-order-meter-readings-value-div').html("");
            for (row = 0; row < woMeterReadingValues.length; row++) {
            	meterReadingValue = woMeterReadingValues[row];
                var html = 
                	"<input id='workOrderMeterReadingValues" + row + ".woMeterReadingId' name='workOrderMeterReadingValues[" + row + "].woMeterReadingId' value='" + CustomValidation.nullValueReplace(meterReadingValue.woMeterReadingId) + "' type='hidden' >" +
                    "<input id='workOrderMeterReadingValues" + row + ".woMeterReadingValue'	name='workOrderMeterReadingValues[" + row + "].woMeterReadingValue' value='" + CustomValidation.nullValueReplace(meterReadingValue.woMeterReadingValue) + "' type='hidden' >" +
                    "<input id='workOrderMeterReadingValues" + row + ".woMeterReadingValueIndex' name='workOrderMeterReadingValues[" + row + "].woMeterReadingValueIndex' value='" + CustomValidation.nullValueReplace(meterReadingValue.woMeterReadingValueIndex) + "' type='hidden' >"+
	                "<input id='workOrderMeterReadingValues" + row + ".woMeterReadingValueAddedDate' name='workOrderMeterReadingValues[" + row + "].woMeterReadingValueAddedDate' value='" + CustomValidation.nullValueReplace(meterReadingValue.woMeterReadingValueAddedDate) + "' type='hidden' >";
                $('#work-order-meter-readings-value-div').append(html);
            }
        }
    };

    var initMeterReadingTable = function () {
        $('#tbl_wo_meterReading >tbody').html("");
        if (meterReadings.length > 0) {
            $.each(meterReadings, function (index, meterReading) {
            	meterReading['meterReadingIndex'] = index;
            	var vars = [index, meterReading.meterReadingId, meterReading.meterReadingName];
                var newRow = "<tr> " +
                    "<input type='hidden' name='meterReadings[" + index + "].id' value='" + CustomValidation.nullValueReplace(meterReading.id) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingId' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingId) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingName' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingName) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingAssetId' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingAssetId) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingAssetName' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingAssetName) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingCurrentValueId' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingCurrentValueId) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingCurrentValueIndex' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingCurrentValueIndex) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingCurrentValue' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingCurrentValue) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].meterReadingDescription' value='" + CustomValidation.nullValueReplace(meterReading.meterReadingDescription) + "'> " +
                    "<input type='hidden' name='meterReadings[" + index + "].version' value='" + CustomValidation.nullValueReplace(meterReading.version) + "'> " +
                    "<td> " + (index + 1) + "</td>" +
                    "<td> " + meterReading.meterReadingAssetName + "</td>" +
                    "<td> " + meterReading.meterReadingName + "</td>" +
                    "<td> " + meterReading.meterReadingCurrentValue + "</td>" +
                    "<td> " + meterReading.meterReadingDescription + "</td>" +
                    "<td style='text-align:center'>"	+
                    ButtonUtil.getCommonBtnEdit('MeterReadingTab.meterReadingEditView', index) +
                    ButtonUtil.getCommonBtnAddWithMultipleVars('MeterReadingTab.meterReadingValueAddView', index, vars) +
                    ButtonUtil.getCommonBtnHistory('MeterReadingTab.meterReadingHistoryView', meterReading.meterReadingId) +
                    ButtonUtil.getCommonBtnDelete('MeterReadingTab.removeMeterReading', index) +
                    "</td>" +
                    "</tr>";
                $("#tbl_wo_meterReading > tbody").append(newRow);
            });
        }
        else {
            CustomComponents.emptyTableRow("tbl_wo_meterReading", 6, "Please Add Meter Reading to Work Order.");
        }
    };
    
    var getWoMeterReadingByIndex = function (index) {
        for (var i = 0; i < meterReadings.length; i++) {
            if (meterReadings[i].meterReadingIndex == index) {
                return meterReadings[i];
            }
        }
    };

    var isMeterReadingAlreadyAdd = function (meterReading) {
        for (var i = 0; i < meterReadings.length; i++) {
            if ((meterReadings[i].meterReadingId == meterReading.meterReadingId) && 
            		(meterReadings[i].meterReadingAssetId == meterReading.meterReadingAssetId)) {
                return true;
            }
        }
        return false;
    };
    
    var removeMeterReading = function (index) {
        for (var i = 0; i < meterReadings.length; i++) {
            if (meterReadings[i].meterReadingIndex == index) {
            	meterReadings.splice(i, 1);
                initMeterReadingTable();
                break;
            }
        }
    };
    
    var updateIndexes = function () {
        for (var i = 0; i < meterReadings.length; i++) {
        	meterReadings[i].meterReadingIndex = i;
        }
    };

    return {
    	
    	init: function () {
    		initButtons();
    		initMeterReadingTable();
    	},
    	
    	meterReadingAddView: function () {
    		meterReadingAddView();
    	},
    	
    	meterReadingEditView: function (index) {
        	meterReadingEditView(index);
        },
    	
    	meterReadingHistoryView: function (index) {
    		meterReadingHistoryView(index);
        },
        
        meterReadingValueAddView: function(index, name, meterReadingId){
        	meterReadingValueAddView(index, name, meterReadingId);
        },       
        
        getSelectedAssetMeterReadingValue: function(meterReadingId){
        	getSelectedAssetMeterReadingValue(meterReadingId);
        },
        
        addMeterReading: function () {
            addMeterReading();
        },

        addMeterReadingValue: function () {
        	addMeterReadingValue();
        },

        removeMeterReading: function(index){
        	removeMeterReading(index);
        },
        
        isMeterReadingAlreadyAdd: function (meterReading) {
            isMeterReadingAlreadyAdd(meterReading);
        }

    };
}();

