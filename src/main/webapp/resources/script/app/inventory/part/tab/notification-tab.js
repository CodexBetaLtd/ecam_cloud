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
            var url = '../part/notification-add-modal-view';
            $modal.load(url, '', function () {
            	NotificationAddModal.init( 'common-modal' );
                $modal.modal();
            });
        }, 1000);

    };

    var initNotificationTable = function () {
    	
        $('#tbl_part_notification tbody').html("");

        if (notifications.length > 0) {
            for (var row = 0; row < notifications.length; row++) {
                
            	var partNotification = notifications[row];
            	
            	partNotification['index'] = row;
            	
                var partNotificationId = partNotification.id == null ? '' : partNotification.id;
                var partUserId = partNotification.userId == null ? '' : partNotification.userId;
                var partUserName = partNotification.userName == null ? '' : partNotification.userName;
                var partId = partNotification.woId == null ? '' : partNotification.woId;

                var onStockAdd = partNotification.notifyOnStockAdd == true ? "checked" : " ";
                var onStockRemove = partNotification.notifyOnStockRemove == true ? 'checked' : ''; 

                var htmlRow =
                    "<tr>" +
                    "<input id='notifications_" + row + "' name='PartNotificationDTOs[" + row + "].id' value='" + partNotificationId + "' type='hidden'>" +
                    "<input id='notifications_" + row + "' name='PartNotificationDTOs[" + row + "].partId' value='" + partId + "' type='hidden' >" +
                    "<input id='notifications_" + row + "' name='PartNotificationDTOs[" + row + "].userId' value='" + partUserId + "' type='hidden' >" +
                    "<input id='notifications_" + row + "' name='PartNotificationDTOs[" + row + "].version' value='" + partNotification.version + "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + partUserName + "</td>" +
                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onStockAdd_" + row + "' name='PartNotificationDTOs[" + row + "].notifyOnStockAdd'  type='checkbox' " + onStockAdd + " class='grey clsNotification'>" +
	                    "</div>" +
                    "</td>" +

                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onStockRemove_" + row + "' name='PartNotificationDTOs[" + row + "].notifyOnStockRemove'  type='checkbox' " + onStockRemove + " class='grey clsNotification'>" +
	                    "</div>" +
                    "</td>" +
                    "<td style='text-align:center'>" +
	                	ButtonUtil.getCommonBtnDelete('NotificationTab.removeNotification', row) +
	                "</td></tr>";
                
                $('#tbl_part_notification > tbody:last-child').append(htmlRow);
            }
            initCheckBoxes();
        } else {
            CustomComponents.emptyTableRow("tbl_part_notification", 5, "Please Add Notfication to Part.");
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






