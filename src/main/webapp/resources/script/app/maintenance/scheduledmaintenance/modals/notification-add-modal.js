var NotificationAddModal = function () {
	
	var initButtons = function () {
		
		$('#btn-add-notification').on('click', function () {
			NotificationAddModal.addNotification();
        });
		
	};

	var initNotifyUserSelect = function () {
        $("#notificationUserName").inputClear({
            placeholder: "Select Notify User",
            btnMethod: "NotificationAddModal.initNotifyUserSelectDataTable()",
            tooltip: "Assign User",
        });
    };
    
    var initNotifyUserSelectDataTable = function () {
        var $modal = $('#sm-notification-dt-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/smUserView';
            $modal.load(url, '', function () {
                dtScheduledMaintenanceUsers.smNotifyUsers("NotificationAddModal.setNotifyUser");
                $modal.modal();
            });
        }, 1000);
    };

    var setNotifyUser = function (id, userName) {
        $('#notificationUserId').val(id);
        $('#notificationUserName').val(EncodeDecodeComponent.getBase64().decode(userName));
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
            	notificationUserName: {
                    required: true
                }
            },
            messages: {
            	notificationUserName: "Please Select a User"
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
    		TabNotification.addNotification();
    	}
    };
	
	return {
		
		init: function () {
			initButtons();
			initNotifyUserSelect();
            initValidator();
        },  
        
        initNotifyUserSelectDataTable: function() {
        	initNotifyUserSelectDataTable();
        },

        setNotifyUser: function (id, userName) {
        	setNotifyUser(id, userName);
        },
        
        addNotification: function () {
        	addNotification();
        }

	};
	
}();
