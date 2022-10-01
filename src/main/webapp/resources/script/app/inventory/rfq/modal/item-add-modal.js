var ItemAddModal = function() {

	var initButtons = function() {
		$('#btn-add-rfq-item').on('click', function() {
			ItemAddModal.addRFQItem();
		});
		$('#btn-add-rfq-item-asset').on('click', function() {
			ItemAddModal.getRFQItemAssetView();
		});
		$('#btn-add-rfq-item-part').on('click', function() {
			ItemAddModal.getRFQItemPartView();
		});

	};

	var getRFQItemAssetView = function() {
		var $modal = $('#rfq-item-child-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../rfq/asset-select-modal-view';
			$modal.load(url, '', function() {
				dtRFQItemAsset.initRFQAssetTable();
				$modal.modal();
			});
		}, 1000);
	};

	var getRFQItemPartView = function() {
		var $modal = $('#rfq-item-child-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../rfq/part-select-modal-view';
			$modal.load(url, '', function() {
				dtRFQPart.initRFQAssetTable();
				$modal.modal();
			});
		}, 1000);
	};

	var setRFQItemAsset = function(id, name) {
		$("#itemAssetId").val(id);
		$("#rfqItemAssetName").val(name);
		$("#rfq-item-child-modal").modal('toggle');
	};

	var setRFQItemPart = function(id, name) {
		$("#itemAssetId").val(id);
		$("#rfqItemAssetName").val(name);
		$("#rfq-item-child-modal").modal('toggle');
	};

	var addRFQItem = function() {
		if ($('#item-add-frm').valid()) {
			TabItem.addRFQItem();
			$("#master-modal-datatable").modal('toggle');

		}

	};
    var initValidator = function () {
        var form = $('#item-add-frm');
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
            	rfqItemAssetName : {
            		required : true
            	},
            	itemQtyRequested : {
					required : true
				}
            },
            messages: {
            	rfqItemAssetName: "Please Insert Asset",
            	itemQtyRequested: "Please Insert Qunatity"
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

	return {
		init : function() {
			initButtons();
			initValidator();
		},

		addRFQItem : function() {
			addRFQItem();
		},
		getRFQItemAssetView : function() {
			getRFQItemAssetView();
		},
		getRFQItemPartView : function() {
			getRFQItemPartView();
		},
		setRFQItemPart : function(id, name) {
			setRFQItemPart(id, name);
		},
		setRFQItemAsset : function(id, name) {
			setRFQItemAsset(id, name);
		}
	};

}();