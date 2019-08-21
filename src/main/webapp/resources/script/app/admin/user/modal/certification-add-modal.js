var UserCertificationAddModal = function () {
	
	var initButtons = function () { 
	    
	    $(document).on('click', '#certificationTypeNew', function (event) {
	        event.preventDefault();
	        CertificationTypeAddModal.userCertificationTypeAddView();  
	    });
	    
		$('#add-user-certification-btn').on('click', function () {
			UserCertificationAddModal.addUserCertification();
        });
		
	};
	
	var addUserCertification = function () {
		
		if ( $('#certification-add-frm').valid() ) {
			TabUserCertification.addUserCertification();
		}
		
	};
	
	var initCertificationTypeSelect = function () {
        $("#certificationTypeName").inputClear({
            placeholder: "Select a Certification Type",
            btnMethod: "UserCertificationAddModal.userCertificationView();",
            tooltip: "Cetification Type"
        });
    };    
    
    var userCertificationView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../userProfile/userCertificationtypeview';
            $modal.load(url, '', function () {
            	CertificationTypeTable.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    var initUserCertificationLevelSelect = function () {
        $("#certificationLevelId").select2({
            placeholder: "Select a User Certification Level",
            dropdownParent: $("#common-modal"),
            allowClear: true,
        });
    };

    var initToDatePicker = function () {
        $('#validTo').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };
    
    var initFromDatePicker = function () {
        $('#validFrom').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };
    
    var setCertificationType= function (id, name) {
		$('#certificationTypeId').val(id);
		$('#certificationTypeName').val(name);
		$('#master-modal-datatable').modal('toggle');
	};
	
	var initValidator = function () {
		var form = $('#certification-add-frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        form.validate({
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
                }
            },
            ignore: "",
            rules: {            	
            	name: {
                    required: true
                },
                certificationTypeName: {
                    required: true
                },
                certificationLevelId: {
                    required: true
                },
                validFrom: {
                	required: true
                },
                validTo: {
                	required: true
                }
            },
            messages: {
            	name: "Please Inser a Name",
            	certificationTypeName: "Please Select Certification",
            	certificationLevelId: "Please Select Certification Level",
            	validFrom: "Please Select a From Date",
            	validTo: "Please Select a To Date"
            	
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
            },
            unhighlight: function (element) { // revert the change done by hightlight
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

    return {
        init: function () {
        	initButtons();
            initCertificationTypeSelect();
            initUserCertificationLevelSelect();
            initToDatePicker();
            initFromDatePicker();      
            initValidator();
        },
        
        setCertificationType: function(id, code){
        	setCertificationType(id, code);
        },
        
        userCertificationView: function () {
        	userCertificationView();
        },
        
        addUserCertification: function () {
        	addUserCertification();
        }
    };

}();