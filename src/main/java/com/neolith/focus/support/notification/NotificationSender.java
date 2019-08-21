package com.neolith.focus.support.notification;

import com.neolith.focus.dto.biz.notification.NotificationDTO; 

public abstract class NotificationSender {

	public abstract void send(NotificationDTO notificationDTO); 
}
