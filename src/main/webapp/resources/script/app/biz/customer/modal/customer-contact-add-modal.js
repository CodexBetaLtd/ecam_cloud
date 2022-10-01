var ContactAddModal = function () {	
	
	/*********************************************************************
     * Init Buttons
     *********************************************************************/

    var initButtons = function () {

        $('#btn-add-customer-contact').off('click').on('click', function () {
        	ContactAddModal.addContact();
        });

    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/
    
    var initValidator = function () {

        var form = $('#customer-contact-frm');
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
            	contactName: {
                    required: true
                },
                contactDesignation: {
                    required: true
                },
                contactTelephone: {
                    required: true,
                },
                contactEmail: {
                    required: true,
                    email: true
                }
            },
            messages: {
            	contactName: "Please Insert a Name",
            	contactDesignation: "Please Insert a Designation",
            	contactTelephone: "Please Insert a Telephone",
            	contactEmail: {
            		required: "Please Insert a Email",
            		email: "Please Insert a valid email"
            	}
            },
            invalidHandler: function (event, validator) {
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
            },
            unhighlight: function (element) {
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
    
    var addContact = function () {
        if ($('#customer-contact-frm').valid()) {
        	TabContact.addContact();
        }
    };

    return {

        init: function () {
        	initButtons();
        	initValidator();        	
        },
        
        addContact: function () {
        	addContact();
        }
    };

}();