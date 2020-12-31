
var StockNotificationTab = function () {
	
	var initButtons = function () {
		
		$('#new-stock-notification-btn').on('click', function () {
			StockNotificationTab.notificationAddView();
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
        var $modal = $('#stock-common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/stock-notification-add-modal-view';
            $modal.load(url, '', function () {
            	StockNotificationAddModal.init( 'stock-common-modal' );
                $modal.modal();
            });
        }, 1000);
    };

    var initNotificationTable = function (  ) {
    	
        $('#tbl_stock_notification tbody').html(""); 
        
    	if ( stockNotifications.length > 0 ) { 
    		
    		for (var row = 0; row < stockNotifications.length; row++) {
                
            	var stockNotification = stockNotifications[row]; 

                var stockUserName = stockNotification.userName == null ? '' : stockNotification.userName;

                var onStockAdd = stockNotification.notifyOnStockAdd == true ? "checked" : " ";
                var onStockRemove = stockNotification.notifyOnStockRemove == true ? 'checked' : '';
                var onMinQty = stockNotification.notifyOnMinQty == true ? 'checked' : '';

                var htmlRow =
                    "<tr>" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + stockUserName + "</td>" +
                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onStockAdd_" + row + "' type='checkbox' " + onStockAdd + " class='grey'>" +
	                    "</div>" +
                    "</td>" +
                    
                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onStockRemove_" + row + "' type='checkbox' " + onStockRemove + " class='grey'>" +
	                    "</div>" +
                    "</td>" +

                    "<td>" +
	                    "<div class='checkbox-center'>" +
	                    	"<input id='onMinQty_" + row + "' type='checkbox' " + onMinQty + " class='grey'>" +
	                    "</div>" +
                    "</td>" +
                    
                    "<td style='text-align:center'>" +
	                	ButtonUtil.getCommonBtnDelete('StockNotificationTab.removeNotification', row) +
	                "</td></tr>";
                
                $('#tbl_stock_notification > tbody:last-child').append(htmlRow);
            }
            initCheckBoxes(); 
		} else {
			CustomComponents.emptyTableRow("tbl_stock_notification", 6, "Please Add Notfication to Stock.");
		} 

    };

    var removeNotification = function (index) {
    	stockNotifications.splice(index, 1);
        initNotificationTable();
    };

    return {
    	
    	init: function ( ) {
    		initButtons();
    		initCheckBoxes();
    		initNotificationTable( );
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
