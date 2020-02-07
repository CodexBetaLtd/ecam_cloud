var aodReturnModal = function () {

    var initButtons = function () {
        $(document).on('click', '#btnAODReturnItemAdd', function () {
            aodReturnModal.addAODReturnItem(this);
        });

    };
    var runAodReturnAODNoInput = function () {
        $("#aodReturnAODNo").inputClear({
            placeholder: "Select AOD",
            btnMethod: "aodReturnItemTab.aodReturnAODView()",
            clearMethod: "aodReturnItemTab.partInputClearFromAOD()",
        });
    };


    var runAodReturnAodItemNoInput = function () {
        $("#aodReturnAodItemNo").inputClear({
            placeholder: "Select AOD Item",
            btnMethod: "aodReturnItemTab.aodReturnAODItemView()",
        });
    };


    var initValidator = function () {
        var form = $('#frm_return_item');
        var errorHandler = $('.errorHandler', form);
        jQuery.validator.addMethod("greaterThanAODQuantity", function (value, element) {
            return value <=$("#aodItemQuantity").val() && value > 0
        }, "This item quantity unable to return for seleted AOD");
        var successHandler = $('.successHandler', form);
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
                /*            	aodReturnAODNo: {
                 required: true
                 },*/
                aodReturnAodItemNo: {
                    required: true
                },
                returnQuantity: {
                    required: true,
                    greaterThanAODQuantity: [],
                }

            },
            messages: {
                //aodReturnAODNo: "Please Select a AOD",
                aodReturnAodItemNo: "Please Select a AOD Item",
                returnQuantity: {
                    required: "Please Insert a  Quantity",
                    greaterThanAODQuantity: 'This item quantity unable to return for seleted AOD',
                }
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


    var addAODReturnItem = function (obj) {
        if ($('#frm_return_item').valid()) {
            aodReturnItemTab.addAODReturnItem(obj);
            aodReturnItemTab.populateAODReturnItems();
        }


    };

    return {
        init: function () {
            runAodReturnAODNoInput();
            runAodReturnAodItemNoInput();
            initButtons();
            initValidator();
        },
        addAODReturnItem: function (obj) {
            addAODReturnItem(obj);
        }
    }
}();