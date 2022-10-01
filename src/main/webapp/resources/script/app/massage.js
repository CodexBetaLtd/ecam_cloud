var Message = function() {
    
	var authenticatedUserId = $("#authenticatedUserId").val();

	var getAllMessages = function() {
		$.ajax({
			type : "GET",
			url : "/ECAM/restapi/notification/inbox-messagelist",
			contentType : "application/json",
			dataType : "json",
			success : function(output) {
				messagesList = output;
				populateMessages();
				console.log(output)
			},
			error : function(xhr, ajaxOptions, thrownError) {
				// alert(xhr.status + " " + thrownError);
			},
			error : function(e) {
				// alert("Failed to load site");
			}
		});
	}
	var populateMessages = function() {
		$("#messageList").empty();
		messagesList.sort(function(a,b){
			  return new Date(b.notifyTime) - new Date(a.notifyTime);
			});
		for (var i = 0; i < messagesList.length; i++) {
			var message = messagesList[i];
			$("#messageList").append(getMessageString(message,i));

		}
		messageCount();

	}

	var addMessage = function(messages) {
		if (messages.userId == authenticatedUserId) {
			messagesList.push(messages);
			populateMessages();
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

			stompClient.subscribe('/topic/message', function(obj) {
				var notification = JSON.parse(obj['body']);
				addMessage(notification);
			});

		});
	}

	var getMessageString = function(notification,index) {
		var html = "";
		html = html + '<li >' + '<a onclick="Message.previewNotification('
				+ notification.id +','+index+')">' + '<div class="clearfix">'
				+ '<div class="thread-content">' + '<span class="author"> '
				+ notification.sendUser + '</span>' + '<span class="preview">'
				+ notification.subject + '</span>'
				+ '<span class="time"> ' + timeSince(notification.notifyTime)
				+ '</span>' + '</div>' + '</div>' + '</a>' + '</li>';
		if(notification.isPopup){
			getMessageToast(notification);

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

	var getMessageToast = function(message) {

		$.toast({
			heading : 'New Message',
			text : message.subject,
			hideAfter : 10000,
			// icon: 'info',
			bgColor : '#272727',
			textColor : 'white',
			position : 'bottom-right',
			showHideTransition : 'slide',
			delay : 5000
		})
	}

	var previewNotification = function(id,index) {
		var $modal = $('#notification-view-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '/ECAM/notification/preview?id=' + id;
			$modal.load(url, '', function() {
				initCHEditor();
				$modal.modal();
			});
		}, 1000);
		removeMessages(index);

	};

	var initCHEditor = function() {
		CKEDITOR.replace('contentPreview');		

	};
	
	var removeMessages=function(index){
    	messagesList.splice(index, 1);
    	populateMessages();


	}
	var messageCount = function() {
		var count = messagesList.length
		$(".msg-count").text(count);
		//Message.init();
	}

	return {
		init : function() {
			kafakWebSocketListner();
			getAllMessages();
		},
		previewNotification : function(id) {
			previewNotification(id);
		}
	};
}();