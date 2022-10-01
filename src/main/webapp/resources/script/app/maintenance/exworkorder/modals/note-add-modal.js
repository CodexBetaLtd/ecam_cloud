var NoteAddModal = function () {

    /*********************************************************************
     * Init Buttons
     *********************************************************************/
	
	var initButtons = function() {
	    $('#btn-add-wo-note').on('click', function (event) {
	        event.preventDefault();
	        NoteAddModal.addWoNote();
	    });
	};
	
    /*********************************************************************
     * Init Components
     *********************************************************************/
	
    var runDatePicker = function () {
        $('.date-picker').datepicker({ 
            autoclose: true,
            container: '#picker-container'
        });
        
        $(".date-picker").datepicker().datepicker("setDate", new Date());
    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/

    var runValidator = function () {
        var form = $('#wo-note-add-frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#wo-note-add-frm').validate({
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
            	woNoteDate: {
                    minlength: 2,
                    required: true
                },
                woNote: {
                    required: true
                },
            },
            messages: {
            	woNoteDate: "Please Specify a Date",
            	woNote: "Please Specify a Note",
                
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
    }
    
    var addWoNote = function() {
		if ( $('#wo-note-add-frm').valid()) {
			woNoteTab.addWoNote(); 
		}
	}
    
    return {
        init: function () {
        	runDatePicker();
        	initButtons();
        	runValidator();
        },
        
        addWoNote: function() {
        	addWoNote();
		}
    };
    
}();