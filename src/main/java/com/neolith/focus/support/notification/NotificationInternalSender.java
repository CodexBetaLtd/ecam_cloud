package com.neolith.focus.support.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neolith.focus.dto.biz.notification.NotificationDTO;
import com.neolith.focus.service.notification.api.NotificationService;

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
