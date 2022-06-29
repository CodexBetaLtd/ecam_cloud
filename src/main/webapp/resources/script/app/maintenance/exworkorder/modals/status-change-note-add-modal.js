var StatusChangeModal = function () {

    /*********************************************************************
     * Init Buttons
     *********************************************************************/

    var initButtons = function (id, status) {
	    $('#btn-status-change').on('click', function (event) {
	        event.preventDefault();
            StatusChangeModal.statusChange(id, status);
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
        var form = $('#wo-status-change-note-add-frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#wo-status-change-note-add-frm').validate({
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
            	date: {
                    minlength: 2,
                    required: true
                },
                note: {
                    required: true
                },
            },
            messages: {
            	date: "Please Specify a Date",
            	note: "Please Specify a Note",
                
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

    var statusChange = function (id, status) {
    	var $submitForm = $('#wo_add_frm');
    	var $form = $('#wo-status-change-note-add-frm');
    	
    	var date = $('#woNoteDate').val();
    	var note = $('#woNote').val();
    	var url = '../workorder/statusChange?id=' + id + "&status=" + status + "&date=" + date +"&note=" + note;
    	
		if ( $form.valid() ) {			
			$submitForm.attr('action', url).submit();
		}
		
	};

    var fillData = function (note) {
		$('#woNote').val(note);
	};
    
    return {
        init: function (id, status, note) {
        	runDatePicker();
        	runValidator();
        	fillData(note);
        	initButtons(id, status);
        },

        initButtons: function (id, status) {
            initButtons(id,status);
        },

        statusChange: function (id, status) {
            statusChange(id, status);
        },
    };
    
}();