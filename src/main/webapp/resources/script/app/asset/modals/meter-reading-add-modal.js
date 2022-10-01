var MeterReadingAddModal = function() {

    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearMeterReadingUnit()
    };
    
    function initInputClearMeterReadingUnit(){
        $("#meterReadingUnitName").inputClear({
            placeholder:"Please specify a unit",
            btnMethod:"MeterReadingAddModal.addMeterReadingUnit()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalViewMeterReadingUnitSelect() {
        var $modal = $('#meter-reading-child-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/view/modal/meter-reading-units';
            $modal.load(url, '', function () {
                DataTableModalMeterReadingUnits.init(
                        "meter-reading-child-modal",
                        "tbl_meter_reading_units",
                        "../../restapi/meterreading-units/tabledata",
                        "setData"
                        );
                $modal.modal();
            });
        }, 1000);
    };

    /********************************************
     * Initialize buttons 
     ********************************************/
    
	var initButtons = function() {
		$('#btn-add-meter-reading').on('click', function() {
			MeterReadingAddModal.addAssetMeterReading();
		});

		$('#btn-new-meter-reading-consumption-variable').on('click', function() {
			MeterReadingAddModal.addAssetMeterReadingVariableModal();
		});

	};
	
	var initCheckBoxes = function() {

		$('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
			checkboxClass : 'icheckbox_minimal-grey',
			radioClass : 'iradio_minimal-grey',
			increaseArea : '10%', // optional
		});
		initMultipleMeterReadingEnable();

		$('#isMultipleMeterReading').on('ifChecked', function(event) {
			initMultipleMeterReadingEnable();
		});
		
		$('#isMultipleMeterReading').on('ifUnchecked', function(event) {
			initMultipleMeterReadingEnable();
		});

	};

	var initMultipleMeterReadingEnable = function(){
		if($('#isMultipleMeterReading').prop("checked")){
			$('#formula').prop('readonly', false);
			$("#btn-new-meter-reading-consumption-variable").attr("disabled", false);
			$(".multipleReading").show();
		}else{
			$('#formula').val("");
			$('#formula').prop('readonly', true);
			$("#btn-new-meter-reading-consumption-variable").attr("disabled", true);
			var variables=[];
			MeterReadingConsumptionVariableAddModal.loadMeterReadingVariable(variables);
			$(".multipleReading").hide();
		}
	}
	
	var addAssetMeterReading = function() {
		if ($('#asset-meter-reading-add-frm').valid()) {
			TabMeterReading.addAssetMeterReading();
		}
	};

	var initValidator = function() {
		var form = $('#asset-meter-reading-add-frm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);
		form.validate({
					errorElement : "span", // contain the error msg in a span
					errorClass : 'help-block',
					errorPlacement : function(error, element) {
					 if (element.attr("type") == "radio" || element.attr("type") == "checkbox") {
							error.insertAfter($(element).closest('.form-group').children('div').children().last());
						} else if (element.attr("name") == "dd"
								|| element.attr("name") == "mm"
								|| element.attr("name") == "yyyy"
								|| element.closest('.input-group').has(
										'.input-group-btn').length
								|| element.closest('.form-group').has(
										'.input-group-addon').length) {
							error.insertAfter($(element).closest('.form-group')
									.children('div'));
						} else if (element.closest('.form-group').has(
								'.select2').length) {
							error.insertAfter($(element).closest('.form-group')
									.children('span'));
						} else {
							error.insertAfter(element);
						}
					},
					ignore : "",
					rules : {
					    meterReadingUnitName : {
							required : true
						},
						meterReadingName : {
							required : true
						}
					},
					messages : {
					    meterReadingUnitName : "Please Select a Meter Reading Unit",
						meterReadingName : "Please Insert a Name"
					},
					invalidHandler : function(event, validator) {
					    successHandler.hide();
						errorHandler.show();
					},
					highlight : function(element) {
						$(element).closest('.help-block').removeClass('valid');
						$(element).closest('.form-group').removeClass(
								'has-success').addClass('has-error').find(
								'.symbol').removeClass('ok').addClass(
								'required');
					},
					unhighlight : function(element) { // revert the change
														// done by hightlight
						$(element).closest('.form-group').removeClass(
								'has-error');
					},
					success : function(label, element) {
						label.addClass('help-block valid');
						$(element).closest('.form-group').removeClass(
								'has-error').addClass('has-success').find(
								'.symbol').removeClass('required').addClass(
								'ok');
					},
					submitHandler : function(form) {
						successHandler.show();
						errorHandler.hide();
						return true;
					}
				});
	};

    var addAssetMeterReadingVariableModal = function () {
        var $modal = $('#meter-reading-child-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/assetmeterreadingconsumptionvariablemodelview';
            $modal.load(url, '', function () {
                $modal.modal();
                MeterReadingConsumptionVariableAddModal.init();
            });
        }, 1000);
    };
	return {

		init : function() {
			initButtons();
			initInputClearComponents();
			initValidator();
			initCheckBoxes()

		},

		addAssetMeterReading : function() {
			addAssetMeterReading();
		},
		
		addMeterReadingVariable:function(){
			addMeterReadingVariable();
		},
		
		addAssetMeterReadingVariableModal:function(){
			addAssetMeterReadingVariableModal();
		},
		
		addMeterReadingUnit: function () {
		    initModalViewMeterReadingUnitSelect();
        }

	};

}();