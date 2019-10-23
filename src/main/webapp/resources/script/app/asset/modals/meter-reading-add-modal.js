var MeterReadingAddModal = function() {

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

		$('#isMultipleMeterReading').on('ifChecked', function(event) {
			$('#formula').prop('readonly', false);
			$("#btn-new-meter-reading-consumption-variable").attr("disabled", false);
			$(".multipleReading").show();
		});
		
		$('#isMultipleMeterReading').on('ifUnchecked', function(event) {
			$('#formula').val("");
			$('#formula').prop('readonly', true);
			$("#btn-new-meter-reading-consumption-variable").attr("disabled", true);
			var variables=[];
			MeterReadingConsumptionVariableAddModal.loadMeterReadingVariable(variables);
			$(".multipleReading").hide();

		});

	};
	var addAssetMeterReading = function() {
		if ($('#asset-meter-reading-add-frm').valid()) {
			TabMeterReading.addAssetMeterReading();
		}
	};

	var runMeterReadingUnitSelect = function() {

		$("#meterReadingUnitId").select2({
			placeholder : "Select a Meter Reading Unit",
			allowClear : true,
			dropdownParent : $("#master-modal-datatable")
		});
	};

	var initValidator = function() {
		var form = $('#asset-meter-reading-add-frm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);
		form
				.validate({
					errorElement : "span", // contain the error msg in a span
											// tag
					errorClass : 'help-block',
					errorPlacement : function(error, element) { // render error
																// placement for
																// each input
																// type
						if (element.attr("type") == "radio"
								|| element.attr("type") == "checkbox") { // for
																			// chosen
																			// elements,
																			// need
																			// to
																			// insert
																			// the
																			// error
																			// after
																			// the
																			// chosen
																			// container
							error.insertAfter($(element).closest('.form-group')
									.children('div').children().last());
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
						meterReadingUnitId : {
							required : true
						},
						meterReadingName : {
							required : true
						}
					},
					messages : {
						meterReadingUnitId : "Please Select a Meter Reading Unit",
						meterReadingName : "Please Insert a Name"
					},
					invalidHandler : function(event, validator) { // display
																	// error
																	// alert on
																	// form
																	// submit
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
        var $modal = $('#meter-reading-consumption-variable-modal');
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
			runMeterReadingUnitSelect();
			initValidator();
			initCheckBoxes()
			$(".multipleReading").hide();

		},

		addAssetMeterReading : function() {
			addAssetMeterReading();
		},
		
		addMeterReadingVariable:function(){
			addMeterReadingVariable();
		},
		addAssetMeterReadingVariableModal:function(){
			addAssetMeterReadingVariableModal();
		}

	};

}();