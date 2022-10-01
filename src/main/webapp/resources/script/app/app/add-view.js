var AppAdd = function () {

    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#app_add_frm');
        var errorHandler = $('.errorHandler', form);
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
                name: {
                    required: true
                },
                rate: {
                    required: true,
                    number: true
                }
            },
            messages: {
                rate: {
                    required: "Please Insert a Rate",
                    number: "Rate must be a number"
                },
                name: "Please Insert a name"
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

    var selectEstimateModal = function () {
//        if ($('#customerId').val() != null && $('#customerId').val() > 0) {
//            var $modal = $('#common-modal');
//            CustomComponents.ajaxModalLoadingProgressBar();
//            setTimeout(function () {
//                var url = '../invoice/estimate-select-view';
//                $modal.load(url, '', function () {
//                    EstimateSelectModal.init();
//                    $modal.modal();
//                });
//            }, 1000);
//        } else {
//            alert("Please Select Customer First.");
//        }
    };

    return {

        init: function () {
            initValidator();
        },

        selectEstimateModal: function () {
            selectEstimateModal();
        }
    };
}();