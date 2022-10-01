var TaxAdd = function() {

    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };
	var initTaxTypeSwaitch =function(){
        var state = false;
        if ($('#taxType').val() == 'FIX_VALUE') {
            state = 'FIX_VALUE';
        }
        $('#taxType').bootstrapSwitch({
            onText: " $ Value",
            offText: " % Percentage",
            state: state
        }).on('switchChange.bootstrapSwitch', function (event, state) {
            if ($('#taxType').val() == 'FIX_VALUE') {
                $('#taxType').val('PERCENTAGE');
            } else {
                $('#taxType').val('FIX_VALUE');
            }
        });
	}
	var saveTax = function () {

        var form = $("#taxAddForm");
		
		if (form.valid()) {
			$.ajax({
	             url: form.attr('action'),
	             type: 'POST',
	             data: form.serialize(),
	             success: function(response) {
                     $("#taxTab").empty().append(response);
                     TaxAdd.init();
	             }
	         });
		}
	};

	var runValidator = function() {

        var form = $('#taxAddForm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);

        $('#taxAddForm').validate(
		{
			errorElement : "span", // contain the error msg in a span tag
			errorClass : 'help-block',
			errorPlacement : function(error, element) { // render error placement for each input type
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
			ignore : "",
			rules : {
				name : {
					minlength : 2,
					required : true
				},
				description : {
					minlength : 2,
					required : true
				},
				businessId : {
					required : true
				}
			},
			messages : {
				name : "Please Specify a Name",
				description : "Please Specify a Description",
				businessId : "Please Select Business"
			},
			invalidHandler : function(event, validator) { //display error alert on form submit
				successHandler.hide();
				errorHandler.show();
			},
			highlight : function(element) {
				$(element).closest('.help-block').removeClass('valid');
				// display OK icon
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
				// add the Bootstrap error class to the control group
			},
			unhighlight : function(element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error');
				// set error class to the control group
			},
			success : function(label, element) {
				label.addClass('help-block valid');
				// mark the current input as valid and display OK icon
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
			},

			submitHandler : function(form) {
				successHandler.show();
				errorHandler.hide();
				// submit form
				return true;
			}
		});
	};

	return {

		init : function() {
			runValidator();
			runBusinessSelect();
			ValueTab.init();
			initTaxTypeSwaitch();
		},
		
		runValidator: function(){
			runValidator();
		},
		
		saveTax : function() {
			saveTax();
		},
	
	};
}();