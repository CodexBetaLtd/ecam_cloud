var NotificationAddModal = function () {
	
	var initButtons = function () {
		
		$('#add-notification-btn').on('click', function () {
			NotificationAddModal.addNotification();
        });
		
	};
	
	/********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearUser();
    };
    
    function initInputClearUser() {
        $("#woNotificationUserName").inputClear({
            placeholder: "Select assigned User",
            btnMethod: "NotificationAddModal.addUser()",
        });
    };
    
    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalUserSelect() {
        
        var businessId = $("#businessId option:selected").val(); 
        
        if (businessId != null && businessId != "" && businessId != undefined) { 
            var $modal = $('#wo-notification-add-child-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../workorder/view/modal/users';
                $modal.load(url, '', function () {
                    DatatableModalUsers.init(
                            "wo-notification-add-child-modal",
                            "tbl_users",
                            "../restapi/users/usersbybusinessid?id=" + businessId,
                            "setNotificationUser");
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Bisuness first");
        }

    };
	
	var initValidator = function () {
        var form = $('#notification-add-frm');
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
            	woNotificationUserName: {
                    required: true
                }
            },
            messages: {
            	woNotificationUserName: "Please Select a User To Notify"
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
    
    var addNotification = function () {
    	if ( $("#notification-add-frm").valid() ) {
    		NotificationTab.addNotification();
    	}
    };
	
	return {
		
		init: function () {
			initButtons();
			initInputClearComponents();
			NotificationTab.initCheckBoxes();
            initValidator();
        },  
        
        addUser: function() {
            initModalUserSelect();
        },
        
        addNotification: function () {
        	addNotification();
        }

	};
	
}();
