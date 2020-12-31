
/*********************************************************************
 * Work Order Notification
 *********************************************************************/
var NotificationTab = function () {
	
	var initButtons = function () {
		
		$('#new-notification-btn').on('click', function () {
			NotificationTab.notificationAddView();
        });
		
	}
	
	var initCheckBoxes = function () {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%', // optional
        });
    };

    var notificationAddView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/notification-add-modal-view';
            $modal.load(url, '', function () {
            	NotificationAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var addNotification = function () {
        var notification = {}
        notification['index'] = "";    // null
        notification['id'] = ""; // null
        notification['version'] = "";
        notification['userId'] = $('#woNotificationUserId').val();
        notification['userName'] = $('#woNotificationUserName').val();
        notification['notifyOnAssignment'] = $("#notifyOnAssignment").prop("checked");
        notification['notifyOnStatusChange'] = $("#notifyOnStatusChange").prop("checked");
        notification['notifyOnCompletion'] = $("#notifyOnCompletion").prop("checked");
        notification['notifyOnTaskCompleted'] = $("#notifyOnTaskCompleted").prop("checked");
        notification['notifyOnOnlineOffline'] = $("#notifyOnOnlineOffline").prop("checked");
        if (!isNotificationAlreadyAdd(notification)) {
            notifications.push(notification);
            initNotificationTable();
            $('#master-modal-datatable').modal("toggle");
        } else {
            alert('User Already Added');
        }       
    }

    var initNotificationTable = function () {
    	
        $('#tbl_workOrder_notification tbody').html("");

        if (notifications.length > 0) {
            for (var row = 0; row < notifications.length; row++) {
                
            	var woNotification = notifications[row];
            	
            	woNotification['index'] = row;
                var woNotificationId = woNotification.id == null ? '' : woNotification.id;
                var woUserId = woNotification.userId == null ? '' : woNotification.userId;
                var woUserName = woNotification.userName == null ? '' : woNotification.userName;
                var woId = woNotification.woId == null ? '' : woNotification.woId;

                var onAssignment = woNotification.notifyOnAssignment == true ? "checked" : " ";
                var onStatusChange = woNotification.notifyOnStatusChange == true ? 'checked' : '';
                var onCompletion = woNotification.notifyOnCompletion == true ? 'checked' : '';
                var onTaskCompleted = woNotification.notifyOnTaskCompleted == true ? 'checked' : '';
                var onOnlineOffline = woNotification.notifyOnOnlineOffline == true ? 'checked' : '';

                var htmlRow =
                    "<tr>" +
                    "<input id='notifications_" + row + "' name='notifications[" + row + "].id' value='" + woNotificationId + "' type='hidden'>" +
                    "<input id='notifications_" + row + "' name='notifications[" + row + "].workOrderId' value='" + woId + "' type='hidden' >" +
                    "<input id='notifications_" + row + "' name='notifications[" + row + "].userId' value='" + woUserId + "' type='hidden' >" +
                    "<input id='notifications_" + row + "' name='notifications[" + row + "].version' value='" + woNotification.version + "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + woUserName + "</td>" +
                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input id='onAssignment_" + row + "' name='notifications[" + row + "].notifyOnAssignment'  type='checkbox' " + onAssignment + " class='grey clsNotification'>" +
                    "</div>" +
                    "</td>" +

                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input id='onStatusChange_" + row + "' name='notifications[" + row + "].notifyOnStatusChange'  type='checkbox' " + onStatusChange + " class='grey clsNotification'>" +
                    "</div>" +
                    "</td>" +

                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input id='onCompletion_" + row + "' name='notifications[" + row + "].notifyOnCompletion'  type='checkbox' " + onCompletion + " class='grey clsNotification'>" +
                    "</div>" +
                    "</td>" +

                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input id='onTaskCompleted_" + row + "' name='notifications[" + row + "].notifyOnTaskCompleted' type='checkbox' " + onTaskCompleted + " class='grey clsNotification'>" +
                    "</div>" +
                    "</td>" +

                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input id='onOnlineOffline_" + row + "' name='notifications[" + row + "].notifyOnOnlineOffline' type='checkbox' " + onOnlineOffline + " class='grey clsNotification'>" +
                    "</div>" +
                    "</td>" +
                    "<td style='text-align:center'>" +
	                	ButtonUtil.getCommonBtnDelete('NotificationTab.removeNotification', row) +
	                "</td></tr>";
                
                $('#tbl_workOrder_notification > tbody:last-child').append(htmlRow);
            }
            initCheckBoxes();
        } else {
            CustomComponents.emptyTableRow("tbl_workOrder_notification", 8, "Please Add notfication to Work Order.");
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






