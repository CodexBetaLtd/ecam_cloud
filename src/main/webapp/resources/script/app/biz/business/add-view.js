var BusinessAdd = function () {
	
    var runVirtualBusinessSelect = function () {
        $("#virtualBusinessOwnerId").select2({
            placeholder: "Select a Business ",
            allowClear: true
        });
    };
	
	var runValidator = function () {
		
        var form = $('#businessAddForm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#businessAddForm').validate({
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
                name: {
                    minlength: 2,
                    required: true
                },
                code: {
                    minlength: 2,
                    required: true
                },
                currencyId: {
                    required: true
                },
                businessClassficationId: {
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
                name: "Please Specify a Name",
                code: "Please Specify a Code",
                currencyId: "Please Specify a Currency",
                businessClassficationId: "Please Specify a Business Classfication",
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
    		runValidator();
    		runVirtualBusinessSelect();
    	}	
    };
}();