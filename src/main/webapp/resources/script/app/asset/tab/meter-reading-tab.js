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
	
	let scope = {} //define scope for evaluate equation
	
	var initMeterReadingValueAddButton = function () {
		initFormulaScope();
		
		$('#btn-add-meter-reading-value').on('click', function () {
			TabMeterReading.addAssetMeterReadingValue();
	    });	
		
		$('#btn-new-meter-reading-consumption').on('click', function () {			
			TabMeterReading.addAssetMeterReadingConsumptionModal();			
	    });	
		
		$( ".evaluate" ).focusout(function() {
			getValueList(this)
			evaluateFormulaString();			
		})
		
	};
	 

	var initCheckBoxes = function () {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%', // optional
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
                MeterReadingConsumptionVariableAddModal.loadMeterReadingVariable(getAssetMeterReadingByIndex(meterReadingIndex).consumptionVariableDTO)
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
                    MeterReadingConsumptionVariableAddModal.resetVariableTable();
                    $modal.modal();
                });
            }, 1000);
        }
    };

    var addAssetMeterReadingValueModal = function (meterReadingIndex,meterReadingName,consumptionFormula,isMultipelMeterReading) {
    	var $modal = $('#master-modal-datatable');
    	CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../../asset/assetmeterreadingvaluemodelview';
    		$modal.load(url, '', function () {
    			$('#valueMeterReadingIndex').val(meterReadingIndex);
    			$('#valueMeterReadingName').val(meterReadingName);
    			$('.multipleReading').hide();
    			if(isMultipelMeterReading){
    				$('#formula').val(consumptionFormula);
    				$('.multipleReading').show();
    				setMeterReadingVariableList(meterReadingIndex)
    			}
    			//$('#value').val(0);
    			$modal.modal();
    			initMeterReadingValueAddValidator();
    			initMeterReadingValueAddButton();
    			//MeterReadingConsumptionAddModal.resetConsumptionTable();
    		});
    	}, 1000);
    };
    var setMeterReadingVariableList=function(meterReadingIndex){
    	var meterReadingVariableList=assetMeterReadings[meterReadingIndex].consumptionVariableDTO;
    	if (meterReadingVariableList.length > 0) {
			var row, meterReadingVariable;
			$("#meter-reading-consumption-value-tbl> tbody").html("");
			for (row = 0; row < meterReadingVariableList.length; row++) {
				meterReadingVariable = meterReadingVariableList[row];
				var html = "<tr id='consumption_row_"+row+ "' >"
				+ "<input id='meterReadingVariableList_"+ row+ "__variableId'  value='"+ meterReadingVariable.variableId+ "' type='hidden' >"
						+ "<input id='meterReadingVariableList_"+ row+ "_meteReadingUnitName'  value='"+ meterReadingVariable.meteReadingUnitName+ "' type='hidden' >"
						+ "<input id='meterReadingVariableList_"+ row+ "_meteReadingUnitId'  value='"+ meterReadingVariable.meteReadingUnitId+ "' type='hidden' >"
						+ "<input id='meterReadingVariableList_"+ row+ "_variableName'  value='"+ meterReadingVariable.variable+ "' type='hidden' >"
						+ "<td>"+ meterReadingVariable.variable + "</td>"
						+ "<td>"
						+ "<input id='meterReadingVariableList_"+ row+ "_variableValue' class='evaluate' value='' type='text' >"
						+ "</td>"
						+ "</tr>";
				$('#meter-reading-consumption-value-tbl > tbody:last-child')
						.append(html);
			}
		} else {
			$("#meter-reading-consumption-value-tbl > tbody")
					.html(
							"<tr><td colspan='6' align='center'>Please Add Asset Meter Reading for the Consumption.</td></tr>");
		}
    }
    var addAssetMeterReadingConsumptionModal = function () {
        var $modal = $('#meter-reading-consumption-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/assetmeterreadingconsumptionmodelview';
            $modal.load(url, '', function () {
                $modal.modal();
                MeterReadingConsumptionAddModal.init();
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
        assetMeterReading['isMultipleMeterReading'] = $("#isMultipleMeterReading").prop("checked");
        assetMeterReading['consumptionFormula'] = $("#formula").val();
        assetMeterReading['meterReadingConsumptionValues'] = "";
        assetMeterReading['consumptionVariableDTO'] = [];
        addMeterReadingConunsumptionVariables(assetMeterReading);
        addAssetMeterReadingToList(assetMeterReading);
        resetAssetMeterReadingHtmlTable();
        $('#master-modal-datatable').modal('toggle');
    };
    
    
    var addMeterReadingConunsumptionVariables=function(assetMeterReading){
    	var lenght=$("#meter-reading-variable-tbl > tbody > tr").length;
    	for(var i=0;i<lenght;i++){
    		var meterReadingConsumptionVariable={};
    		meterReadingConsumptionVariable['id'] = $('#meterReadingVariableList_'+i+'_id').val();
    		meterReadingConsumptionVariable['variable'] = $('#meterReadingVariableList_'+i+'_variableName').val();
    		meterReadingConsumptionVariable['description'] = $('#meterReadingVariableList_'+i+'_description').val();
    		meterReadingConsumptionVariable['version'] = $('#meterReadingVariableList_'+i+'_version').val();
    		meterReadingConsumptionVariable['meteReadingUnitName'] = $('#meterReadingVariableList_'+i+'_meteReadingUnitName').val();
    		meterReadingConsumptionVariable['meteReadingUnitId'] = $('#meterReadingVariableList_'+i+'_meteReadingUnitId').val();
    		assetMeterReading['consumptionVariableDTO'].push(meterReadingConsumptionVariable);
    	}
    }

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
        $('#isMultipleMeterReading').prop('checked',assetMeterReading['isMultipleMeterReading']);
        $( "#formula" ).val(assetMeterReading['consumptionFormula'] );
    };


 
    var resetAssetMeterReadingHtmlTable = function () {

        if (assetMeterReadings.length > 0) {
            var row, assetMeterReading;
            $("#asset_meter_reading_tbl > tbody").html("");
            for (row = 0; row < assetMeterReadings.length; row++) {
                assetMeterReading = assetMeterReadings[row];
                var isMultipleMeterReading = assetMeterReading.isMultipleMeterReading == true ? 'checked' : '';
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
                    "<input id='assetMeterReadings" + row + ".consumptionFormula' name='assetMeterReadings[" + row + "].consumptionFormula' value='" + assetMeterReading.consumptionFormula + "' type='hidden' >" +
                  setMeterReadingValriableToList(row,assetMeterReading)+
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + assetMeterReading.meterReadingName + "</span></td>" +
                    "<td>" + assetMeterReading.meterReadingCurrentValue + "</td>" +
                    "<td class='hidden-xs'><span>" + assetMeterReading.meterReadingUnitName + "</span></td>" +
                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input id='isMultipleMeterReading" + row + "' name='assetMeterReadings[" + row + "].isMultipleMeterReading'  type='checkbox' " + isMultipleMeterReading + " class='grey' readonly>" +
                    "</div>" +
                    "</td>"+
                    "<td class='hidden-xs'><span>" + assetMeterReading.meterReadingAvgValue + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("TabMeterReading.editAssetMeterReadingModal",  assetMeterReading.meterReadingIndex) +
                	ButtonUtil.getCommonBtnHistory("TabMeterReading.assetMeterReadingHistoryModal",  assetMeterReading.meterReadingId) +
                    "<a onclick='TabMeterReading.addAssetMeterReadingValueModal(" + assetMeterReading.meterReadingIndex + ",\"" + assetMeterReading.meterReadingName +  "\",\"" + assetMeterReading.consumptionFormula +"\",\""+isMultipleMeterReading+"\");' class='btn btn-xs btn-blue tooltips' data-placement='top' data-original-title='Add Value' >" +
                    "<i class='clip-plus-circle-2' ></i></a>\n\n" +
                    ButtonUtil.getCommonBtnDelete("TabMeterReading.removeAssetMeterReading", assetMeterReading.meterReadingIndex) +
                    "</div></td></tr>";

                $('#asset_meter_reading_tbl > tbody:last-child').append(html);
            }
        } else {

            $("#asset_meter_reading_tbl > tbody").html("<tr><td colspan='6' align='center'>Please Add Asset Meter Reading for the Asset.</td></tr>");
        }

    };
    
    
    var setMeterReadingValriableToList=function(index,assetMeterReading){
    	var html="";
    	var consumptionVariables=assetMeterReading['consumptionVariableDTO'];
        if (consumptionVariables.length > 0) {
            var row, consumptionVariable;
            for (row = 0; row < consumptionVariables.length; row++) {
            	consumptionVariable = consumptionVariables[row];
            	html = html
            	+"<input id='assetMeterReadings" + index + "consumptionVariable"+row+"variable' name='assetMeterReadings[" + index + "].consumptionVariableDTO["+row+"].variable' value='" + consumptionVariable.variable + "' type='hidden'>"
            	+"<input id='assetMeterReadings" + index + "consumptionVariable"+row+"meteReadingUnitId' name='assetMeterReadings[" + index + "].consumptionVariableDTO["+row+"].meteReadingUnitId' value='" + consumptionVariable.meteReadingUnitId + "' type='hidden'>"
            	+"<input id='assetMeterReadings" + index + "consumptionVariable"+row+"version' name='assetMeterReadings[" + index + "].consumptionVariableDTO["+row+"].version' value='" + consumptionVariable.version + "' type='hidden'>"
                +"<input id='assetMeterReadings" + index + "consumptionVariable"+row+"id' name='assetMeterReadings[" + index + "].consumptionVariableDTO["+row+"].id' value='" + consumptionVariable.id + "' type='hidden'>"
            }
        }
        return html;
    }

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
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'isMultipleMeterReading', assetMeterReading.isMultipleMeterReading);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'consumptionFormula', assetMeterReading.consumptionFormula);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'consumptionVariableDTO', assetMeterReading.consumptionVariableDTO);
    	CustomValidation.validateFieldNull(assetMeterReadingObj, 'meterReadingConsumptionValues', assetMeterReading.meterReadingConsumptionValues);

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
            assetMeterReadingValue['meterReadingConsumptionFunction'] = $('#formula').val();

            var time = new Date();
            assetMeterReadingValue['assetMeterReadingValueAddedDate'] = time.getTime();
            assetMeterReadingValue['assetMeterReadingValueAddedDateStr'] = moment(time).format('YYYY-MM-DD hh:mm:ss A');
            assetMeterReadingValue['valueConsumptionDTO'] = []
            addMeterReadingConunsumption(assetMeterReadingValue);
            addAssetMeterReadingValueToList(assetMeterReadingValue);
            
            $('#master-modal-datatable').modal("toggle")
    	}        
    };
    var addMeterReadingConunsumption=function(assetMeterReadingValue){
    	var lenght=$("#meter-reading-consumption-value-tbl > tbody > tr").length;
    	for(var i=0;i<lenght;i++){
    		var meterReadingConsumption={};
    		meterReadingConsumption['variable'] = $('#meterReadingVariableList_'+i+'_variableName').val();
    		meterReadingConsumption['value'] = $('#meterReadingVariableList_'+i+'_variableValue').val();
    		assetMeterReadingValue['valueConsumptionDTO'].push(meterReadingConsumption);
    	}
    }

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
                    "<input id='assetMeterReadingValues" + row + ".assetMeterReadingValueIndex' name='assetMeterReadingValues[" + row + "].assetMeterReadingValueIndex' value='" + assetMeterReadingValue.assetMeterReadingValueIndex + "' type='hidden' >"+
                    "<input id='assetMeterReadingValues" + row + ".meterReadingConsumptionFunction' name='assetMeterReadingValues[" + row + "].meterReadingConsumptionFunction' value='" + assetMeterReadingValue.meterReadingConsumptionFunction + "' type='hidden' >";
               
                $('#asset-meter-readings-value-div').append(html);
                resetMeterReadingConsumption(assetMeterReadingValue,row)
            }
        }
    };
    
    var resetMeterReadingConsumption=function(assetMeterReadingValue,valueIndex){
        if (assetMeterReadingValue.valueConsumptionDTO.length > 0) {
            var row, assetMeterReadingCosumption;
            
            for (row = 0; row <assetMeterReadingValue.valueConsumptionDTO.length; row++) {
            	assetMeterReadingCosumption = assetMeterReadingValue.valueConsumptionDTO[row];
                var html = "<input id='assetMeterReadingValues"+valueIndex+"valueConsumptionDTO" + row + "variable' name='assetMeterReadingValues[" + valueIndex + "].valueConsumptionDTO["+row+"].variable' value='" + assetMeterReadingCosumption.variable + "' type='hidden'>" +
                "<input id='assetMeterReadingValues"+valueIndex+"valueConsumptionDTO" + row + "value' name='assetMeterReadingValues[" + valueIndex + "].valueConsumptionDTO["+row+"].value' value='" + assetMeterReadingCosumption.value + "' type='hidden'>";
                $('#asset-meter-readings-value-div').append(html);

            }

        }
    }
   
    
    var evaluateFormulaString=function(){   
        var entry=$("#formula").val();
        var value=0; 
   		value=math.evaluate(entry, scope);
    	$("#value").val(value)
    }
    
    var initFormulaScope=function(){
   		var variablels=getVariableList();
         for(var i=0;i<variablels.length;i++){
        	scope[variablels[i]]=0
         }
	}
    
    
    var getValueList=function(obj){
    	scope[obj.parentNode.previousSibling.childNodes[0].data]=obj.value
    }
    
    var getVariableList=function(){
 	   var formula=$("#formula").val();
 	   if(formula!=null && formula!=undefined && formula!=""){
 		  return formula.match(/[^+/*()-]+/g).filter(
 	    		    function(x) { return !/^{.+?}$/.test(x) })
 	   }
    	return []
    }
    
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
    		initCheckBoxes();
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

        addAssetMeterReadingValueModal: function (meterReadingIndex, meterReadingName,consumptionFormula,isMultipelMeterReading) {
            addAssetMeterReadingValueModal(meterReadingIndex, meterReadingName,consumptionFormula,isMultipelMeterReading);
        },

        assetMeterReadingHistoryModal: function (meterReadingId) {
        	assetMeterReadingHistoryModal(meterReadingId);
        },
        addAssetMeterReadingConsumptionModal: function () {
        	addAssetMeterReadingConsumptionModal();
        }
    };

}();
