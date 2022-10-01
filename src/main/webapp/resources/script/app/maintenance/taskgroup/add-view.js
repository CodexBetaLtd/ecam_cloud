var TaskGroupAdd = function () {

    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    var runSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };


    var runAssetBusinessSelect = function () {
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
                    runSiteSelect();
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

    var taskGroupValidator = function () {
    	
        var form = $('#frm_task_group');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frm_task_group').validate({
            errorElement: "span",
            errorClass: 'help-block',
            errorPlacement: function (error, element) {
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") {
                    // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
                name: {
                    minlength: 2,
                    required: true
                }
            },

            messages: {
                subject: "Please Specify a Name"
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

    return {
        init: function () {
            runBusinessSelect();
            runSiteSelect();
        	taskGroupValidator();
            // runAssetBusinessSelect();
        }
    };
}();

