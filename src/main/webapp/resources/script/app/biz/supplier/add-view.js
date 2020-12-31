var SupplierBusinessAdd = function () {

    var initDropDownBusiness = function () {
        $("#businessId").select2({
            placeholder: "Please Specify the Business",
            allowClear: true
        });
    };

    var runCurrencySelect = function () {
        $("#currencyId").select2({
            placeholder: "Select a Primary Currency",
            allowClear: true
        });
    };

    var runClassificationSelect = function () {
        $("#businessClassificationId").select2({
            placeholder: "Select a Business Classification",
            allowClear: true
        });
    };

    var runCountrySelect = function () {
        $("#countryId").select2({
            placeholder: "Select a Country",
            allowClear: true
        });
    };

    var runProviderSwitch = function () {
        var state = false;
        if ($('#isProvider').val() == 'true') {
            state = true;
        }
        $('#isProvider').bootstrapSwitch({
            onText: "Supplier",
            offText: "Provider",
            state: state
        }).on('switchChange.bootstrapSwitch', function (event, state) {
            if ($('#isProvider').val() == 'true') {
                $('#isProvider').val('false');
            } else {
                $('#isProvider').val('true');
            }
        });
    };
    var runValidator = function () {
        var form = $('#supplierBusinessAddForm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#supplierBusinessAddForm').validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else {
                    error.insertAfter(element);
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
                code: {
                    minlength: 3,
                    required: true,
                    remote: {
                        url: "../validate/supplier/validate-by-code",
                        type: "GET",
                        data: {
                            id: $('#supId').val()
                        }
                    }
                },
                name: {
                    minlength: 2,
                    required: true
                },
                currencyId: {
                    required: true
                },
//                businessTypeId: {
//                    required: true
//                },
                countryId: {
                   required: true
                }
            },
            messages: {
                code: {
                    required: "Please Specify a Code",
                    minlength: "Code Contain atleast 3 Characters",
                    remote: jQuery.validator.format("Code {0} is already taken.")
                },
                name: "Please Specify a Name",
                currencyId: "Please Specify a Currency",
               // businessClassficationId: "Please Specify a Business Classfication",
//                businessTypeId: "Please Specify a Business Type",
                countryId: "Please Specify a Country",
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
            unhighlight: function (element) { 
            	// revert the change done by hightlight
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

    return {
        init: function () {
           // runValidator();
            runCurrencySelect();
            initDropDownBusiness();
            runClassificationSelect();
            runCountrySelect();
            runProviderSwitch();
        }
    };
}();