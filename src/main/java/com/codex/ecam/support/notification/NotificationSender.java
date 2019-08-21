package com.codex.ecam.support.notification;

import com.codex.ecam.dto.biz.notification.NotificationDTO; 

public abstract class NotificationSender {

	public abstract void send(NotificationDTO notificationDTO); 
}
