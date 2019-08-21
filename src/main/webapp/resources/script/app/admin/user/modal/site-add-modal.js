 var SiteAddModal = function () {
	 
	var initButtons = function () {
		
		$('#add-site-btn').on('click', function () {
			SiteAddModal.addSite();
        });
		
	};
	
	var addSite = function () {
		if ( $('#site-add-frm').valid() ) {
			TabSites.addSite();
		}
	};

    var initSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site/Facility",
            dropdownParent: $("#common-modal"),
            allowClear: true,
        });
    };
    
    var initValidator = function () {
        var form = $('#site-add-frm');
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
            	siteId: {
                    required: true
                },
                userGroupList: {
                    required: true
                }
            },
            messages: {
            	siteId: "Please Select a Site",
            	userGroupList: "Please Select Atleast On User Group"
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
            initSiteSelect();
            initValidator();
        },
    
	    addSite : function() {
			addSite();
		}
    };
    
}();