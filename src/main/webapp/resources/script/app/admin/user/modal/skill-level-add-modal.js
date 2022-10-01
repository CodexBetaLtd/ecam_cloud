var UserSkillAdd = function () {
	
	var initBusinessSelect = function () {
        $("#businessId").select2({
            allowClear: true,
            placeholder: "Select a Business",
            dropdownParent: $("#stackable-modal")
        });
    };
	
	var runValidator = function () {
        var form = $('#userSkillLevelForm');
	    var errorHandler = $('.errorHandler', form);
	    var successHandler = $('.successHandler', form);
	    form.validate({
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
	            	skill: {
	                    minlength: 2,
	                    required: true
	                },
	                description: {
	                	minlength: 2,
	                	required: true,
	                },
	                businessId: {
	                    required: true
	                }	               
	            },
	            messages: {
	            	skill: "Please specify a Skill Level Name",
	            	description: "Please describe Skill Level",
	            	businessId: "Please Select a Business"
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
    
	var userSkillLevelSave = function() {
		var $modal = $('#stackable-modal');
		var form = $('#userSkillLevelForm');

		if (form.valid()) {
			$.ajax({
				url : form.attr('action'),
				type : 'POST',
				data : form.serialize(),
				success : function(response) {
					$modal.empty().append(response);
					UserSkillAdd.init();
					UserSkillLevel.getSkillLevelTable(); 
					$modal.modal();
				},
				error : function(response) {
					console.log(response);
				}
			});
		}
	};
		
	var userSkillLevelDelete = function(id) {
	
    	var $modal = $('#master-modal-datatable');

          $.ajax({
              url: "../userskilllevel/skillleveldelete?id=" + id,
              type: 'GET',
              success: function (response) {
            	  UserAdd.SkillLevelView();
              }
          });
 
	};
		
    return {
    
    	init: function () {
    		runValidator();
    		initBusinessSelect();
    	},
    	
    	userSkillLevelSave:function(){
    		userSkillLevelSave();
    	},
    	
    	userSkillLevelDelete:function(id){
    		userSkillLevelDelete(id);
    	}
    	
    };
}();