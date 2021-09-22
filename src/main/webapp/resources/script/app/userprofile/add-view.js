var MyAccount = function () {

    //Check change password checkbox status.
    var checkPasswordChangeBox = function () {
        
        $("input").on("ifChanged", function () {

            if (!document.getElementById('changePassword').checked) {
                
                $("#currentPassword").attr('disabled', 'disabled');
                $("#password").attr('disabled', 'disabled');
                $("#password_again").attr('disabled', 'disabled');
                
            } else {
                $("#currentPassword").removeAttr('disabled');
                $("#password").removeAttr('disabled');
                $("#password_again").removeAttr('disabled');
            }
        });

    };

    var runValidator = function () {
        var form = $('#myProfileForm');

        var errorHandler = $('.errorHandler', form);

        var successHandler = $('.successHandler', form);

        $('#myProfileForm').validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',

            errorPlacement: function (error, element) {
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") {
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else {
                    error.insertAfter(element);
                }
            },

            ignore: "",

            rules: {
                fullName: {
                    minlength: 2,
                    required: true
                },
                emailAddress: {
                    minlength: 2,
                    required: true,
                    email: true
                },
                userTitle: {
                    minlength: 2,
                    required: true
                }
            },


            messages: {
                fullName: "Please specify a Name",
                emailAddress: {
                    email: "Your email address must be in the format of name@domain.com",
                    required: "Please specify a vaild email"
                },
                userTitle: "Please specify a user titel"
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
                // submit form
                return true;
            }
        });
    };

    var runCustomValidator = function () {

        $("#fullName").rules("add", {
            required: true,
            messages: {
                required: "Please Specify the Full Name"
            }
        });

        $("#currentPassword").rules("add", {
            required: true,
            messages: {
                required: "Please Specify the Current Password"
            }
        });

        $("#password").rules("add", {
            required: true,
            minlength: 6,
            messages: {
                required: "Please Specify the Password"
            }
        });

        $("#password_again").rules("add", {
            required: true,
            minlength: 6,
            equalTo: "#password",
            messages: {
                required: "Password does not match"
            }
        });
    };

    return {
        init: function () {
            runValidator();
            runCustomValidator();
            checkPasswordChangeBox();
        }
    };

}();