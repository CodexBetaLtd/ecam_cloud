var SparePartAddModal = function () {

	var initButtons = function () {
		$('#btn-add-spare-part').on('click', function () {
			SparePartAddModal.addSparePart();
		});
	};

	var addSparePart = function () {
		if ($('#spare-part-add-frm').valid()) {
			SparePartTab.addSparePart(aodItemObj());
		}
	};

	var aodItemObj = function() {
    	var aodItems = {}
    	
    	aodItems['index'] = CustomValidation.nullValueReplace($("#sparePartindex").val());
		aodItems['id'] = CustomValidation.nullValueReplace($("#spareId").val());
        aodItems['partId'] = CustomValidation.nullValueReplace($("#sparePartPartId").val());
    	aodItems['partCode'] = CustomValidation.nullValueReplace($("#sparePartPartCode").val());
    		aodItems['description'] = CustomValidation.nullValueReplace($("#sparePartDescription").val());
		aodItems['quantity'] = CustomValidation.nullValueReplace($("#sparePartQuantity").val()); 
		aodItems['version'] = CustomValidation.nullValueReplace($("#sparePartVersion").val()); 
		
		return aodItems;
	};
	var runSparPartInput = function () {
		$("#sparePartPartCode").inputClear({
			placeholder: "Select A  Spare Part",
			btnMethod: "SparePartAddModal.sparePartSelectModal()",
		})
	};

	var sparePartSelectModal = function () {
		var $modal = $('#meter-reading-consumption-variable-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function () {
			var url = '../sparepartselectmodalview';
			$modal.load(url, '', function () {
				$modal.modal();
				var tableId="part_tbl";
				var url="../../restapi/part/spare-part";
				var method="SparePartAddModal.setSparePart";
				dtSparePart.dtReceiptAsset(tableId,url,method);
			});
		}, 1000);
	}

	var initValidator = function () {
		var form = $('#spare-part-add-frm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);
		form
			.validate({
				errorElement: "span", // contain the error msg in a span
				// tag
				errorClass: 'help-block',
				errorPlacement: function (error, element) { // render error
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
				ignore: "",
				rules: {
					sparePartPartCode: {
						required: true
					},
					sparePartQuantity: {
						required: true
					}
				},
				messages: {
					sparePartPartCode: "Please Select a Spare Part",
					sparePartQuantity: "Please Insert Spare Part Quantity"
				},
				invalidHandler: function (event, validator) { // display
					// error
					// alert on
					// form
					// submit
					successHandler.hide();
					errorHandler.show();
				},
				highlight: function (element) {
					$(element).closest('.help-block').removeClass('valid');
					$(element).closest('.form-group').removeClass(
						'has-success').addClass('has-error').find(
							'.symbol').removeClass('ok').addClass(
								'required');
				},
				unhighlight: function (element) { // revert the change
					// done by hightlight
					$(element).closest('.form-group').removeClass(
						'has-error');
				},
				success: function (label, element) {
					label.addClass('help-block valid');
					$(element).closest('.form-group').removeClass(
						'has-error').addClass('has-success').find(
							'.symbol').removeClass('required').addClass(
								'ok');
				},
				submitHandler: function (form) {
					successHandler.show();
					errorHandler.hide();
					return true;
				}
			});
	};

	var setSparePart = function (id, code) {
		$('#sparePartPartId').val(id);
		$('#sparePartPartCode').val(code);
		$('#meter-reading-consumption-variable-modal').modal('toggle');

	};
	return {

		init: function () {
			initButtons();
			runSparPartInput();
			initValidator();

		},

		addSparePart: function () {
			addSparePart();
		},

		sparePartSelectModal: function () {
			sparePartSelectModal();
		},
		setSparePart: function (id, code) {
			setSparePart(id, code);
		}

	};

}();