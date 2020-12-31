package com.codex.ecam.support.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.service.notification.api.NotificationService;

@Component
public class NotificationInternalSender extends NotificationSender {
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public void send(NotificationDTO notificationDTO) {
		try {
			notificationService.saveSystemNotification(notificationDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
}
