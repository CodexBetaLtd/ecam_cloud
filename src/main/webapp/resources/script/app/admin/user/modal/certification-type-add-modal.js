var CertificationTypeAddModal = function () {
	
	var initButtons = function () {  
	    $('#btn-save-certification-type').on('click', function ( ) { 
	        CertificationTypeAddModal.addCertificationType();  
	    }); 
	};
	
	var userCertificationTypeAddView = function(){
    	var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../userProfile/usercertificationadd';
    		$modal.load(url, '', function (){
    			CertificationTypeAddModal.init();
    			$modal.modal();
    		});
    	}, 1000);
	};  
	
	var addCertificationType = function() {
		if ($('#certificationAddForm').valid()) {
			CertificationTypeAddModal.certificationTypeSave();
		}
	}; 
	
	var initBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true,
            dropdownParent: $("#stackable-modal")
        });
    };
	
	var initValidator = function () {
		
        var form = $('#certificationAddForm');
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
	            	certificationType: {
	                    required: true
	                },
	                businessId : {
	                	required: true
	                }
	            },
	            messages: {
	            	certificationType: {
	            		required : "Please specify a Certification Type"
	            	},
	            	businessId: {
	            		required : "Please specify a Business"
	            	}
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
	    
	var certificationTypeSave = function() {
		var $modal = $('#stackable-modal');
		var form = $('#certificationAddForm');
		if (form.valid()) {
			$.ajax({
				url : form.attr('action') + '?module=user',
				type : 'POST',
				data : form.serialize(),
				success : function(response) {
					$modal.empty().append(response);
					$modal.modal(); 
					CertificationTypeAddModal.init();
					CertificationTypeTable.init();
				},
				error : function(response) {
					console.log(response);
				}
			});
		}
	};
		
    return {
    
    	init: function () { 
    		initValidator();
    		initBusinessSelect();
    		initButtons();
    	},
        
        userCertificationTypeAddView: function(){
    	   userCertificationTypeAddView();
        },
        
        addCertificationType: function() {
        	addCertificationType();
		},
		
		certificationTypeSave: function() {
			certificationTypeSave();
		}
    };
}();