var NotificationTab = function () {
	
	var initButtons = function () {
		
		$('#new-notification-btn').on('click', function () {
			NotificationTab.notificationAddView();
        });
		
	}
	
	var initCheckBoxes = function () {

        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey center',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%',
            disabledClass: 'disabled', // optional
        });

    };

    var notificationAddView = function () {

        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../rfq/notification-add-modal-view';
            $modal.load(url, '', function () {
            	NotificationAddModal.init( 'common-modal' );
                $modal.modal();
            });
        }, 1000);

    };

    var initNotificationTable = function () {
    	
        $('#tbl_rfq_notification tbody').html("");

        if (notifications.length > 0) {
            for (var row = 0; row < notifications.length; row++) {
                
            	var rfqNotification = notifications[row];
            	
            	rfqNotification['index'] = row;
            	
                var rfqNotificationId = rfqNotification.id == null ? '' : rfqNotification.id;
                var rfqUserId = rfqNotification.userId == null ? '' : rfqNotification.userId;
                var rfqUserName = rfqNotification.userName == null ? '' : rfqNotification.userName;

                var OnStatusChannged = rfqNotification.notifyOnStatusChannged == true ? "checked" : " ";

                var htmlRow =
                    "<tr>" +
                    "<input id='notifications_" + row + "' name='notificationDTOs[" + row + "].id' value='" + rfqNotificationId + "' type='hidden'>" +
                    "<input id='notifications_" + row + "' name='notificationDTOs[" + row + "].userId' value='" + rfqUserId + "' type='hidden' >" +
                    "<input id='notifications_" + row + "' name='notificationDTOs[" + row + "].version' value='" + rfqNotification.version + "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + rfqUserName + "</td>" +
                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onAdd_" + row + "' name='notificationDTOs[" + row + "].notifyOnStatusChannged'  type='checkbox' " + OnStatusChannged + " class='grey clsNotification'>" +
	                    "</div>" +
                    "</td>" +
                    "<td style='text-align:center'>" +
	                	ButtonUtil.getCommonBtnDelete('NotificationTab.removeNotification', row) +
	                "</td></tr>";
                
                $('#tbl_rfq_notification > tbody:last-child').append(htmlRow);
            }
            initCheckBoxes();
        } else {
            CustomComponents.emptyTableRow("tbl_rfq_notification", 5, "Please Add Notification to Part.");
        }

    };

    var removeNotification = function (index) {

        notifications.splice(index, 1);
        initNotificationTable();

    };

    return {
    	
    	init: function () {
    		initButtons();
    		initCheckBoxes();
    		initNotificationTable();
    	},
        
        notificationAddView: function () {
            notificationAddView();
        },

        addNotification: function () {
        	addNotification();
        },

        removeNotification: function (index) {
            removeNotification(index);
        },

        initNotificationTable: function () {
        	initNotificationTable();
        },
        
        isNotificationAlreadyAdd: function (notification) {
            isNotificationAlreadyAdd(notification);
        },
        
        initCheckBoxes: function (){
        	initCheckBoxes();
        }
    };
}();






