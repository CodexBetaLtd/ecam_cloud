var NotificationAddModal = function () {
	
	var setModal = function (modalName) {
		this.modalName = modalName;
	};
	
	var initButtons = function () {
		
		$('#add-part-notification-btn').on('click', function () {
			NotificationAddModal.addNotification();
        });
		
	};
	
	var initInputClear = function () {
    	
        $("#partNotificationUserName").inputClear({
            placeholder: "Select a Notify User",
            btnMethod: "NotificationAddModal.notifyUserSelectModal()",
            tooltip: "Select User"
        });
        
    };

	var notifyUserSelectModal = function () {
		
    	var bizId = $('#businessId').val();
    	
    	if ( bizId != null && bizId > 0 ) { 
    		var $modal = $('#part-notification-add-child-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../part/user-select-modal-view';
	            $modal.load(url, '', function () {
                    UserSelectModal.init(bizId, "NotificationAddModal.setNotifyUser");
	                $modal.modal();
	            });
	        }, 1000);
        } else {
			alert("Please Select a Business First.");
        }

    };

    var setNotifyUser = function (id, userName) { 
        $('#partNotificationUserId').val(id);
        $('#partNotificationUserName').val(userName); 
        $("#part-notification-add-child-modal").modal('toggle');
    };
	
	var initValidator = function () {

        var form = $('#part-notification-add-frm');
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
            	partNotificationUserName: {
                    required: true
                }
            },
            messages: {
            	partNotificationUserName: "Please Select a User To Notify"
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

        if ($("#part-notification-add-frm").valid()) {
    		var notification = {}
            notification['index'] = "";    // null
            notification['id'] = ""; // null
            notification['version'] = "";
            notification['userId'] = $('#partNotificationUserId').val();
            notification['userName'] = $('#partNotificationUserName').val();
            notification['notifyOnStockAdd'] = $("#notifyOnStockAdd").prop("checked");
            notification['notifyOnStockRemove'] = $("#notifyOnStockRemove").prop("checked"); 
            
            if (!isNotificationAlreadyAdd(notification)) {
                notifications.push(notification);
                NotificationTab.initNotificationTable();
                
                $('#'+ this.modalName).modal('toggle'); 
            } else {
                alert('User Already Added');
            }     
    	}

    };

    var isNotificationAlreadyAdd = function (notification) {

        for (var i = 0; i < notifications.length; i++) {
            if (notifications[i].userId == notification.userId) {
                return true;
            }
        }
        return false;

    };
	
	return {
		
		init: function ( modalName ) {
			setModal( modalName ); 
			initButtons();
			initInputClear();
			NotificationTab.initCheckBoxes();
            initValidator();
        },  
        
        notifyUserSelectModal: function() {
        	notifyUserSelectModal();
        },

        setNotifyUser: function (id, userName) {
        	setNotifyUser(id, userName);
        },
        
        addNotification: function () {
        	addNotification();
        }

	};
	
}();
