var ReceiptOrderItemReportView = function () {

	var initValidator = function () {
        var form = $('#frmReportReceiptOrderItem');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frmReportReceiptOrderItem').validate({
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
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {                
            	dateReceivedFrom: {
                    required: true
                },
                dateReceivedTo: {
                    required: true
                }
            },
            messages: {
            	dateReceivedFrom: "Please specify a From Date",
            	dateReceivedTo: "Please specify a To Date"
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
            unhighlight: function (element) { // revert the change done by hightlight
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
	
    var runSupplierInput = function () {
        $("#supplierName").inputClear({
            placeholder: "Select Supplier",
            btnMethod: "ReceiptOrderItemReportView.supplierView()",
        });
    };

    var supplierView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptOrderItem/supplierView';
            $modal.load(url, '', function () {
                var dt_url = "../../restapi/supplies/getSupplierDataTable";
                var func = "ReceiptOrderItemReportView.setSupplier";
                dtSupplier.getSupplier(dt_url, func);
                $modal.modal();
            });
        }, 1000);
    };
    
    var setSupplier = function (id, name) {
        $("#supplierId").val(id);
        $("#supplierName").val(name);
        $("#supplierName").attr('readonly', true);
        $('#common-modal').modal("hide");
    };


    return {
        init: function () {
        	initValidator();
            runSupplierInput();
        },
        
        supplierView: function () {
            supplierView();
        },
        
        setSupplier: function (id, name) {
            setSupplier(id, name);
        },


    };
}();