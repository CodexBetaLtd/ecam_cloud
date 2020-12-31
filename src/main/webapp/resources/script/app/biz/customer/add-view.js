var CustomerAdd=function (){
	
    var initCountrySelect = function () {
        $("#customerCountryId").select2({
            placeholder: "Select a Country",
            allowClear: true
        });
    };
    
    var initBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };
    
    var runValidator = function () {
        var form = $('#customer_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#customer_add_frm').validate({
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
            	
            	customerName:{
            		required: true,
            	},
                code: {
                    required: true,
                },
                assetCategoryId: {
                    required: true
                },
                businessId: {
                    required: true
                },
                businessTypeId: {
                	required: true
                },
                siteId: {
                    required: true
                }
            },
            messages: {
                code:  "Please Specify a Customer name.",
                customerCode: "Please Specify a Customer code",
                customerAddress: "Please Specify a customer Address",
                customerTelephone: "Please Specify a Customer Telephone",
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
            initBusinessSelect();
            initCountrySelect();
            runValidator();
        }
    };
}();