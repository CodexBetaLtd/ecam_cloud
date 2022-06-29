var Notification = function() {

    var authenticatedUserId = $("#authenticatedUserId").val();

	var getAllNotification = function() {
		$.ajax({
			type : "GET",
			url : "ECAM/restapi/notification/inbox-notificationlist",
			contentType : "application/json",
			dataType : "json",
			success : function(output) {
				notificationList = output;
				populateNotification();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				// alert(xhr.status + " " + thrownError);
			},
			error : function(e) {
				// alert("Failed to load site");
			}
		});
	};
	
	var populateNotification = function() {
		$("#notificationList").empty();
		notificationList.sort(function(a, b) {
			return new Date(b.notifyTime) - new Date(a.notifyTime);
		});
		for (var i = 0; i < notificationList.length; i++) {
			var notification = notificationList[i];
			$("#notificationList").append(getNotificationtring(notification, i));

		}
		notificationCount();

	}

	var addNotification = function(notification) {
		if (notification.userId == authenticatedUserId) {
			notificationList.push(notification);
			populateNotification();
		}
	}

	var kafakWebSocketListner = function() {
		var sock = new SockJS('http://localhost:8090/ws-notification');
		sock.onopen = function() {
			console.log('open');
			sock.send('test');
		};

		sock.onmessage = function(e) {
			console.log('message', e.data);
			sock.close();
		};

		sock.onclose = function() {
			console.log('close');
		};

		stompClient = Stomp.over(sock);

		stompClient.connect({}, function(frame) {

			stompClient.subscribe('/topic/notification', function(obj) {
				var notification = JSON.parse(obj['body']);
				addNotification(notification);
			});

		});
	}

	var getNotificationtring = function(notification, index) {
		var html = "";
		html = html
				+ '<li>'
				+ '<a onclick="Notification.previewNotification('
				+ notification.id +','+index+')">'
				+ '<span class="label label-primary"><i class="fa fa-user"></i></span>'
				+ '<span class="message no-overflow"> '+notification.subject+'</span>'
				+ '<span class="time">'+timeSince(notification.notifyTime)+'</span>' + '</a>' + '</li>';
		if (notification.isPopup) {
			getNotificationToast(notification);

		}

		return html;
	}

	function timeSince(date) {

		var seconds = Math.floor((new Date() - date) / 1000);
		var interval = seconds / 31536000;
		if (interval > 1) {
			// return Math.floor(interval) + " years";
			return moment(date).format('YYYY-MM-DD HH:mm')
		}
		interval = seconds / 2592000;
		if (interval > 1) {
			// return Math.floor(interval) + " months";
			return moment(date).format('YYYY-MM-DD HH:mm')
		}

		interval = seconds / 86400;
		if (interval > 2) {
			return Math.floor(interval) + "days ago";
		}
		if (interval > 1 && interval < 2) {
			return "A day ago";
		}

		interval = seconds / 3600;
		if (interval > 2) {
			return Math.floor(interval) + " hours ago";
		}
		if (interval > 1 && interval < 2) {
			return "An hour ago";
		}

		interval = seconds / 60;
		if (interval > 2) {
			return Math.floor(interval) + " minutes ago";
		} else {
			return "Just Now";
		}
		// return Math.floor(seconds) + " seconds";
		return moment(date).format('YYYY-MM-DD HH:mm')
	}

	var getNotificationToast = function(notification) {

		$.toast({
			heading : notification.subject,
			text : notification.content,
			hideAfter : 10000,
			icon: 'info',
			position : 'bottom-left',
			showHideTransition : 'slide',
			delay : 5000
		})
	}

	var previewNotification = function(id, index) {
		var $modal = $('#notification-view-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '/ECAM/notification/preview?id=' + id;
			$modal.load(url, '', function() {
				//initCHEditor();
				$modal.modal();
			});
		}, 1000);
		removeNotification(index);

	};

	var initCHEditor = function() {
		CKEDITOR.replace('contentPreview');

	};

	var removeNotification = function(index) {
		notificationList.splice(index, 1);
		populateNotification();

	}
	var notificationCount = function() {
		var count = notificationList.length
		$(".notif-count").text(count);
	};
	

    var getInboxUnreadCount = function() {
        $.ajax({
            type : "GET",
            url : $("#ntf-url").attr('href'),
            contentType : "application/json",
            dataType : "json",
            success : function(output) {
                if (parseFloat(output) > 0) {     
                    $("#inbox-count").text(output);
                    $("#inbox-count").show()
                    $("#ntf-dot").show()
                } else {                    
                    $("#inbox-count").hide()
                    $("#ntf-dot").hide()
                }
            },
            error : function(xhr, ajaxOptions, thrownError) {
                // alert(xhr.status + " " + thrownError);
            },
            error : function(e) {
                // alert("Failed to load site");
            }
        });
    };

	return {
		init : function() {
			kafakWebSocketListner();
			getAllNotification();
		},
		
		previewNotification : function(id) {
			previewNotification(id);
		},
		
		inboxUnreadCount: function () {
		    getInboxUnreadCount();
        }
	};
}();