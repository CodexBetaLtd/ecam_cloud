var AODReturnAdd = function () {

    var runSupplierBusinessSelect = function () {
        $("#supplierId").select2({
            placeholder: "Select a Supplier",
            allowClear: true
        });
    };

    var runShipingContrySelect = function () {
        $("#shipToCountry").select2({
            placeholder: "Select a Country",
            allowClear: true
        });
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            container: $('.date-picker').closest("div"),
            autoclose: true
        });
    };

    var runCheckboxes = function () {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%'
        });
    };

    var runAodReturnAODNoInput = function () {
        $("#aodReturnAODNo").inputClear({
            placeholder: "Select AOD",
            btnMethod: "aodReturnItemTab.aodReturnAODView()",
            clearMethod: "aodReturnItemTab.partInputClearFromAOD()",
        });
    };


    /*
     *load siteList relevant to business
     */
    var runValidator = function () {
        var form = $('#frm_aod_return');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frm_aod_return').validate({
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
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
                code: {
                    required: true
                },
                businessId: {
                    required: true
                },
                siteId: {
                    required: true
                },
                returnNo: {
                    required: true
                },
                returnRefNo: {
                    required: true
                },
            },
            messages: {
                code: "Please Specify a Code",
                businessId: "Please Select a Business",
                siteId: "Please Select a Site",
                returnNo: "Please Specify a AOD Return no",
                returnRefNo: "Please Specify a Ref no",
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


    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    var runBusinessSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };
    var runBusinessSiteFetch = function () {
        $("#businessId").change(function () {
            var businessId = $("#businessId option:selected").val();
            $.ajax({
                type: "GET",
                url: "../asset/getsites?id=" + businessId,
                contentType: "application/json",
                dataType: "json",
                success: function (output) {
                    $("#siteId").find("option").remove();
                    $.each(output, function (key, siteList) {
                        $('#siteId').append($('<option>', {
                            value: siteList.id
                        }).text(siteList.name));
                    });
                    runBusinessSiteSelect();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + " " + thrownError);
                },
                error: function (e) {
                    alert("Failed to load site");
                }
            });
        });
    };


    return {

        init: function () {
            runSupplierBusinessSelect();
            runShipingContrySelect();
            runDatePicker();
            runCheckboxes();
            runValidator();
            runBusinessSelect();
            runBusinessSiteFetch();
            runBusinessSiteSelect();
            runAodReturnAODNoInput();
        },
        initCheckBoxes: function () {
            runCheckboxes();
        }
    };
}();
