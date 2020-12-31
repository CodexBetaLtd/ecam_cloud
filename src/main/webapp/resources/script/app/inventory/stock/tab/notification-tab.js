
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
            checkboxClass: 'icheckbox_minimal-grey center',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%', // optional
        });
    };

    var notificationAddView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stock/notification-add-modal-view';
            $modal.load(url, '', function () {
            	NotificationAddModal.init( 'common-modal' );
                $modal.modal();
            });
        }, 1000);
    };

    var initNotificationTable = function () {
    	
        $('#tbl_stock_notification tbody').html("");

        if (notifications.length > 0) {
            for (var row = 0; row < notifications.length; row++) {
                
            	var stockNotification = notifications[row];
            	
            	stockNotification['index'] = row;
            	
                var stockNotificationId = stockNotification.id == null ? '' : stockNotification.id;
                var stockUserId = stockNotification.userId == null ? '' : stockNotification.userId;
                var stockUserName = stockNotification.userName == null ? '' : stockNotification.userName;

                var onStockAdd = stockNotification.notifyOnStockAdd == true ? "checked" : " ";
                var onStockRemove = stockNotification.notifyOnStockRemove == true ? 'checked' : '';
                var onMinQty = stockNotification.notifyOnMinQty == true ? 'checked' : '';

                var htmlRow =
                    "<tr>" +
                    "<input id='notifications_" + row + "' name='stockNotificationDTOs[" + row + "].id' value='" + stockNotificationId + "' type='hidden'>" +
                    "<input id='notifications_" + row + "' name='stockNotificationDTOs[" + row + "].userId' value='" + stockUserId + "' type='hidden' >" +
                    "<input id='notifications_" + row + "' name='stockNotificationDTOs[" + row + "].version' value='" + stockNotification.version + "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + stockUserName + "</td>" +
                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onStockAdd_" + row + "' name='stockNotificationDTOs[" + row + "].notifyOnStockAdd'  type='checkbox' " + onStockAdd + " class='grey clsNotification'>" +
	                    "</div>" +
                    "</td>" +

                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onStockRemove_" + row + "' name='stockNotificationDTOs[" + row + "].notifyOnStockRemove'  type='checkbox' " + onStockRemove + " class='grey clsNotification'>" +
	                    "</div>" +
                    "</td>" +

                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onMinQty_" + row + "' name='stockNotificationDTOs[" + row + "].notifyOnMinQty'  type='checkbox' " + onMinQty + " class='grey clsNotification'>" +
	                    "</div>" +
                    "</td>" +
                    "<td style='text-align:center'>" +
	                	ButtonUtil.getCommonBtnDelete('NotificationTab.removeNotification', row) +
	                "</td></tr>";
                
                $('#tbl_stock_notification > tbody:last-child').append(htmlRow);
            }
            initCheckBoxes();
        } else {
            CustomComponents.emptyTableRow("tbl_stock_notification", 6, "Please Add Notfication to Part.");
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






