var TabNotification = function () {

	var initButtons = function () {
		
		$('#btn-new-notification').on('click', function () {
			TabNotification.notificationAddView();
        });
		
	};

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
            var url = '../scheduledmaintenance/smNotificationView';
            $modal.load(url, '', function () {
            	NotificationAddModal.init();
                initCheckBoxes();
                $modal.modal();
            });
        }, 1000);
    };

    

    function addNotification() {
        var notification = {};
        notification['index'] = "";    // null
        notification['id'] = ""; // null
        notification['version'] = ""; // null
        notification['userName'] = $('#notificationUserName').val();
        notification['userId'] = $('#notificationUserId').val();
        notification['notifyOnAssignment'] = $("#notifyOnAssignment").prop("checked");
        notification['notifyOnStatusChange'] = $("#notifyOnStatusChange").prop("checked");
        notification['notifyOnCompletion'] = $("#notifyOnCompletion").prop("checked");
        notification['notifyOnTaskCompleted'] = $("#notifyOnTaskCompleted").prop("checked");
        notification['notifyOnOnlineOffline'] = $("#notifyOnOnlineOffline").prop("checked");
        notification['other'] = "";
        if (!isNotificationAlreadyAdd(notification)) {
            notifications.push(notification);
            initNotificationTable();
            $('#master-modal-datatable').modal('toggle');
        } else {
            alert('User Already Added');
        }
    }

    var initNotificationTable = function () {
        $('#tbl_scheduleMaintenance_notification tbody').html("");
        if (notifications.length > 0) {
            for (var row = 0; row < notifications.length; row++) {
                
            	var notifyObj = notifications[row];
            	
                var smNotificationId = notifyObj.id == null ? '' : notifyObj.id;
                var smUserId = notifyObj.userId == null ? '' : notifyObj.userId;
                var smUserName = notifyObj.userName == null ? '' : notifyObj.userName;
                var smId = notifyObj.smId == null ? '' : notifyObj.smId;
                var onAssignment = notifyObj.notifyOnAssignment == true ? "checked" : " ";
                var onStatusChange = notifyObj.notifyOnStatusChange == true ? 'checked' : '';
                var onCompletion = notifyObj.notifyOnCompletion == true ? 'checked' : '';
                var onTaskCompleted = notifyObj.notifyOnTaskCompleted == true ? 'checked' : '';
                var onOnlineOffline = notifyObj.notifyOnOnlineOffline == true ? 'checked' : '';

                var htmlRow =
                    "<tr>" +
                    "<input id='notifications" + row + ".id' name='notifications[" + row + "].id' value='" + smNotificationId + "' type='hidden'>" +
                    "<input id='notifications" + row + ".version' name='notifications[" + row + "].version' value='" + notifyObj.version + "' type='hidden' >" +
                    "<input id='notifications" + row + ".scheduledMaintenanceId' name='notifications[" + row + "].scheduledMaintenanceId' value='" + smId + "' type='hidden' >" +
                    "<input id='notifications" + row + ".userId' name='notifications[" + row + "].userId' value='" + smUserId + "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + smUserName + "</td>" +
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
                	ButtonUtil.getCommonBtnDelete('TabNotification.removeNotification', row) +
                	"</td></tr>";
                $('#tbl_scheduleMaintenance_notification > tbody:last-child').append(htmlRow);
            }
            initCheckBoxes();
        } else {
            CustomComponents.emptyTableRow("tbl_scheduleMaintenance_notification", 8, "Please Add Notification to Scheduled Maintenance");
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

    function removeNotification(index) {
        notifications.splice(index, 1);
        initNotificationTable();
    };

    return {
    	
        init: function () {
            initButtons();
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
        
        isNotificationAlreadyAdd: function (notification) {
            isNotificationAlreadyAdd(notification);
        }

    };

}();