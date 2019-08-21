var TabMeterReading = function () {
	
	var initButtons = function () {
		
		$('#btn-new-meter-reading').on('click', function () {
	        TabMeterReading.addAssetMeterReadingModal();
	    });
		
		$('#addMeterReadingModal').on('hide.bs.modal', function () {
	        $('#addMeterReadingModal').removeData();
	    });

	    $('#meterReadingHistorybtn').on('click', function () {
	        var $modal = $('#common-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../../asset/meterreadinghistoryview';
	            $modal.load(url, '', function () {
	                MeterReadingHistoryModel.init(assetId);
	                $modal.modal();
	            });
	        }, 1000);
	    });
		
	};
	
	var initMeterReadingValueAddButton = function () {
		
		$('#btn-add-meter-reading-value').on('click', function () {
			TabMeterReading.addAssetMeterReadingValue();
	    });
		
	};

    var addAssetMeterReadingModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/assetmeterreadingmodelview';
            $modal.load(url, '', function () {
                MeterReadingAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var editAssetMeterReadingModal = function (meterReadingIndex) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/assetmeterreadingmodelview';
            $modal.load(url, '', function () {
                fillAssetMeterReadingEditForm(getAssetMeterReadingByIndex(meterReadingIndex));
                MeterReadingAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var assetMeterReadingHistoryModal = function (meterReadingId) {
        if (meterReadingId == null || meterReadingId == undefined || meterReadingId == "") {
            alert("Please Save the Asset First.")
        } else {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../../asset/assetmeterreadinghistorymodelview';
                $modal.load(url, '', function () {
                    MeterReadingHistoryModel.init(meterReadingId, "../../restapi/assetmeterreadingvalue/historybymeterreading/");
                    $modal.modal();
                });
            }, 1000);
        }
    };

    var addAssetMeterReadingValueModal = function (meterReadingIndex, meterReadingName) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/assetmeterreadingvaluemodelview';
            $modal.load(url, '', function () {
                $('#valueMeterReadingIndex').val(meterReadingIndex);
                $('#valueMeterReadingName').val(meterReadingName);
                $modal.modal();
                initMeterReadingValueAddValidator();
                initMeterReadingValueAddButton();
            });
        }, 1000);
    };
    
    var initMeterReadingValueAddValidator = function () {
        var form = $('#asset-meter-reading-value-add-frm');
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
            	value: {
                    required: true,
                    number:true
                }
            },
            messages: {
            	value: {
            		required: "Please Insert a value",
            		number: "Please Insert a Numeric Value"
            	}
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

    var addAssetMeterReading = function () {

        var assetMeterReading = {};

        assetMeterReading['meterReadingId'] = $('#meterReadingId').val();
        assetMeterReading['meterReadingIndex'] = $('#meterReadingIndex').val();
        assetMeterReading['meterReadingAssetId'] = $('#meterReadingAssetId').val();
        assetMeterReading['meterReadingName'] = $('#meterReadingName').val();
        assetMeterReading['meterReadingUnitName'] = $('#meterReadingUnitId').select2('data')[0].text;
        assetMeterReading['meterReadingUnitId'] = $('#meterReadingUnitId').val();

        assetMeterReading['meterReadingCurrentValueIndex'] = $('#meterReadingCurrentValueIndex').val();
        assetMeterReading['meterReadingCurrentValueId'] = $('#meterReadingCurrentValueId').val();
        assetMeterReading['meterReadingCurrentValue'] = $('#meterReadingCurrentValue').val();
        assetMeterReading['meterReadingViewName'] = $('#meterReadingViewName').val();
        assetMeterReading['meterReadingDescription'] = $('#meterReadingDescription').val();
        assetMeterReading['meterReadingAvgValue'] = $('#meterReadingAvgValue').val();
        assetMeterReading['version'] = $('#meterReadingVersion').val();

        addAssetMeterReadingToList(assetMeterReading);

        resetAssetMeterReadingHtmlTable();
        $('#master-modal-datatable').modal('toggle');
    };

    var fillAssetMeterReadingEditForm = function (assetMeterReading) {
        $('#meterReadingId').val(assetMeterReading['meterReadingId']);
        $('#meterReadingIndex').val(assetMeterReading['meterReadingIndex']);
        $('#meterReadingAssetId').val(assetMeterReading['meterReadingAssetId']);
        $('#meterReadingName').val(assetMeterReading['meterReadingName']);
        $('#meterReadingUnitId').val(assetMeterReading['meterReadingUnitId']);
        $('#meterReadingCurrentValueIndex').val(assetMeterReading['meterReadingCurrentValueIndex']);
        $('#meterReadingCurrentValueId').val(assetMeterReading['meterReadingCurrentValueId']);
        $('#meterReadingCurrentValue').val(assetMeterReading['meterReadingCurrentValue']);
        $('#meterReadingViewName').val(assetMeterReading['meterReadingViewName']);
        $('#meterReadingDescription').val(assetMeterReading['meterReadingDescription']);
        $('#meterReadingAvgValue').val(assetMeterReading['meterReadingAvgValue']);
        $('#meterReadingVersion').val(assetMeterReading['version']);
    };

    var resetAssetMeterReadingHtmlTable = function () {

        if (assetMeterReadings.length > 0) {
            var row, assetMeterReading;
            $("#asset_meter_reading_tbl > tbody").html("");

            for (row = 0; row < assetMeterReadings.length; row++) {
                assetMeterReading = assetMeterReadings[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingId' name='assetMeterReadings[" + row + "].meterReadingId' value='" + assetMeterReading.meterReadingId + "' type='hidden'>" +
                    "<input id='assetMeterReadings" + row + ".meterReadingIndex' name='assetMeterReadings[" + row + "].meterReadingIndex' value='" + assetMeterReading.meterReadingIndex + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingAssetId' name='assetMeterReadings[" + row + "].meterReadingAssetId' value='" + assetMeterReading.meterReadingAssetId + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingName' name='assetMeterReadings[" + row + "].meterReadingName' value='" + assetMeterReading.meterReadingName + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingViewName' name='assetMeterReadings[" + row + "].meterReadingViewName' value='" + assetMeterReading.meterReadingViewName + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingDescription' name='assetMeterReadings[" + row + "].meterReadingDescription' value='" + assetMeterReading.meterReadingDescription + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingUnitId' name='assetMeterReadings[" + row + "].meterReadingUnitId' value='" + assetMeterReading.meterReadingUnitId + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingUnitName' name='assetMeterReadings[" + row + "].meterReadingUnitName' value='" + assetMeterReading.meterReadingUnitName + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingCurrentValueId' name='assetMeterReadings[" + row + "].meterReadingCurrentValueId' value='" + assetMeterReading.meterReadingCurrentValueId + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingCurrentValueIndex' name='assetMeterReadings[" + row + "].meterReadingCurrentValueIndex' value='" + assetMeterReading.meterReadingCurrentValueIndex + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingCurrentValue' name='assetMeterReadings[" + row + "].meterReadingCurrentValue' value='" + assetMeterReading.meterReadingCurrentValue + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".meterReadingAvgValue' name='assetMeterReadings[" + row + "].meterReadingAvgValue' value='" + assetMeterReading.meterReadingAvgValue + "' type='hidden' >" +
                    "<input id='assetMeterReadings" + row + ".version' name='assetMeterReadings[" + row + "].version' value='" + assetMeterReading.version + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + assetMeterReading.meterReadingName + "</span></td>" +
                    "<td>" + assetMeterReading.meterReadingCurrentValue + "</td>" +
                    "<td class='hidden-xs'><span>" + assetMeterReading.meterReadingUnitName + "</span></td>" +
                    "<td class='hidden-xs'><span>" + assetMeterReading.meterReadingAvgValue + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("TabMeterReading.editAssetMeterReadingModal",  assetMeterReading.meterReadingIndex) +
                	ButtonUtil.getCommonBtnHistory("TabMeterReading.assetMeterReadingHistoryModal",  assetMeterReading.meterReadingId) +
                    "<a onclick='TabMeterReading.addAssetMeterReadingValueModal(" + assetMeterReading.meterReadingIndex + ",\"" + assetMeterReading.meterReadingName + "\");' class='btn btn-xs btn-blue tooltips' data-placement='top' data-original-title='Add Value' >" +
                    "<i class='clip-plus-circle-2' ></i></a>\n\n" +
                    ButtonUtil.getCommonBtnDelete("TabMeterReading.removeAssetMeterReading", assetMeterReading.meterReadingIndex) +
                    "</div></td></tr>";

                $('#asset_meter_reading_tbl > tbody:last-child').append(html);
            }
        } else {

            $("#asset_meter_reading_tbl > tbody").html("<tr><td colspan='5' align='center'>Please Add Asset Meter Reading for the Asset.</td></tr>");
        }

    };

    var addAssetMeterReadingToList = function (assetMeterReading) {

        var assetMeterReadingObj = {}

        if (assetMeterReading.meterReadingIndex != null && assetMeterReading.meterReadingIndex != "" && assetMeterReading.meterReadingIndex >= 0) {

            assetMeterReadingObj = getAssetMeterReadingByIndex(assetMeterReading.meterReadingIndex);
            setCommonDataToAssetMeterReadingObj(assetMeterReading, assetMeterReadingObj);

        } else {
            if (assetMeterReadings.length == 0) {
                assetMeterReadingObj['meterReadingIndex'] = 0;
            } else {
            	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingIndex', assetMeterReadings.length);
            }
            setCommonDataToAssetMeterReadingObj(assetMeterReading, assetMeterReadingObj);
            assetMeterReadings.push(assetMeterReadingObj);
        }
    };

    var setCommonDataToAssetMeterReadingObj = function (assetMeterReading, assetMeterReadingObj) {

    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingId', assetMeterReading.meterReadingId);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingAssetId', assetMeterReading.meterReadingAssetId);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingName', assetMeterReading.meterReadingName);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingUnitName', assetMeterReading.meterReadingUnitName);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingUnitId', assetMeterReading.meterReadingUnitId);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingCurrentValueIndex', assetMeterReading.meterReadingCurrentValueIndex);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingCurrentValueId', assetMeterReading.meterReadingCurrentValueId);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingCurrentValue', assetMeterReading.meterReadingCurrentValue);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingViewName', assetMeterReading.meterReadingViewName);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingDescription', assetMeterReading.meterReadingDescription);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingAvgValue', assetMeterReading.meterReadingAvgValue);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'version', assetMeterReading.version);

    };

    var removeAssetMeterReading = function (index) {
        for (var i = 0; i < assetMeterReadings.length; i++) {
            if (assetMeterReadings[i].meterReadingIndex == index) {
                assetMeterReadings.splice(i, 1);
                updateIndexes();
                resetAssetMeterReadingHtmlTable();
                break;
            }
        }
    };
    
    var initAssetMeterReadings = function () {
    	setAssetMeterReadings(thymeLeafAssetMeterReadings)
    };

    var setAssetMeterReadings = function (assetMeterReadings) {
        for (var i = 0; i < assetMeterReadings.length; i++) {
            addAssetMeterReadingToList(assetMeterReadings[i])
        };
        resetAssetMeterReadingHtmlTable();
    };

    var updateIndexes = function () {
        for (var i = 0; i < assetMeterReadings.length; i++) {
            assetMeterReadings[i].meterReadingIndex = i;
        }
    };


    var addAssetMeterReadingValue = function () {
    	
    	if ( $('#asset-meter-reading-value-add-frm').valid() ) {
    		
    		var assetMeterReadingValue = {};

            assetMeterReadingValue['assetMeterReadingIndex'] = $('#valueMeterReadingIndex').val();
            assetMeterReadingValue['assetMeterReadingName'] = $('#valueMeterReadingName').val();
            assetMeterReadingValue['assetMeterReadingValue'] = $('#value').val();

            var time = new Date();
            assetMeterReadingValue['assetMeterReadingValueAddedDate'] = time.getTime();
            assetMeterReadingValue['assetMeterReadingValueAddedDateStr'] = moment(time).format('YYYY-MM-DD hh:mm:ss A');

            addAssetMeterReadingValueToList(assetMeterReadingValue);
            
            $('#master-modal-datatable').modal("toggle")
    	}        
    };

    var addAssetMeterReadingValueToList = function (assetMeterReadingValue) {

        if (assetMeterReadingValues.length == 0) {
            assetMeterReadingValue['assetMeterReadingValueIndex'] = 0;
        } else {
        	CustomValidation.validateFieldNull(assetMeterReadingValue, 'assetMeterReadingValueIndex', assetMeterReadingValues.length);
        }
        assetMeterReadingValues.push(assetMeterReadingValue);
        updateAssetMeterReading(assetMeterReadingValue);
        resetAssetMeterReadingValues();
    };

    var updateAssetMeterReading = function (assetMeterReadingValue) {
        var assetMeterReading = getAssetMeterReadingByIndex(assetMeterReadingValue.assetMeterReadingIndex);
        CustomValidation.validateFieldNull(assetMeterReading, 'meterReadingCurrentValue', assetMeterReadingValue.assetMeterReadingValue);
        CustomValidation.validateFieldNull(assetMeterReading, 'meterReadingCurrentValueIndex', assetMeterReadingValue.assetMeterReadingValueIndex);
        resetAssetMeterReadingHtmlTable();
    };

    var resetAssetMeterReadingValues = function () {
        if (assetMeterReadingValues.length > 0) {
            var row, assetMeterReadingValue;
            $('#asset-meter-readings-value-div').html("");
            for (row = 0; row < assetMeterReadingValues.length; row++) {
                assetMeterReadingValue = assetMeterReadingValues[row];
                var html = "<input id='assetMeterReadingValues" + row + ".assetMeterReadingIndex' name='assetMeterReadingValues[" + row + "].assetMeterReadingIndex' value='" + assetMeterReadingValue.assetMeterReadingIndex + "' type='hidden'>" +
                    "<input id='assetMeterReadingValues" + row + ".assetMeterReadingValue' name='assetMeterReadingValues[" + row + "].assetMeterReadingValue' value='" + assetMeterReadingValue.assetMeterReadingValue + "' type='hidden' >" +
                    "<input id='assetMeterReadingValues" + row + ".assetMeterReadingValueAddedDate' name='assetMeterReadingValues[" + row + "].assetMeterReadingValueAddedDate' value='" + assetMeterReadingValue.assetMeterReadingValueAddedDate + "' type='hidden' >" +
                    "<input id='assetMeterReadingValues" + row + ".assetMeterReadingValueIndex' name='assetMeterReadingValues[" + row + "].assetMeterReadingValueIndex' value='" + assetMeterReadingValue.assetMeterReadingValueIndex + "' type='hidden' >";
                $('#asset-meter-readings-value-div').append(html);
            }
        }
    };
   
    var getAssetMeterReadingByIndex = function (meterReadingIndex) {
        for (var i = 0; i < assetMeterReadings.length; i++) {
            if (assetMeterReadings[i].meterReadingIndex == meterReadingIndex) {
                return assetMeterReadings[i];
            }
        }
    };

    return {
    	
    	init: function () {
    		initAssetMeterReadings();
    		initButtons();
    	},

        /***********************************************************************
         * AssetMeterReading Add
         **********************************************************************/
        addAssetMeterReadingToList: function (assetMeterReading) {
            addAssetMeterReadingToList(assetMeterReading);
        },

        removeAssetMeterReading: function (index) {
            removeAssetMeterReading(index);
        },

        resetAssetMeterReadingHtmlTable: function () {
            resetAssetMeterReadingHtmlTable();
        },

        setAssetMeterReadings: function (assetMeterReadings) {
            setAssetMeterReadings(assetMeterReadings);
        },

        addAssetMeterReading: function () {
            addAssetMeterReading();
        },

        addAssetMeterReadingValue: function () {
            addAssetMeterReadingValue();
        },

        /**********************************************************
         * Initialize Modals
         *********************************************************/
        addAssetMeterReadingModal: function () {
            addAssetMeterReadingModal();
        },

        editAssetMeterReadingModal: function (meterReadingIndex) {
            editAssetMeterReadingModal(meterReadingIndex);
        },

        addAssetMeterReadingValueModal: function (meterReadingIndex, meterReadingName) {
            addAssetMeterReadingValueModal(meterReadingIndex, meterReadingName);
        },

        assetMeterReadingHistoryModal: function (meterReadingId) {
            assetMeterReadingHistoryModal(meterReadingId);
        }
    };

}();
