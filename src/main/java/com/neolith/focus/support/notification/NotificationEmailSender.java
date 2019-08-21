package com.neolith.focus.support.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.neolith.focus.dto.biz.notification.NotificationDTO;

@Component
public class NotificationEmailSender extends NotificationSender {

	@Autowired
	private MailSender mailSender;

	@Override
	public void send(NotificationDTO notificationDTO) {
		 try { 
			 if (notificationDTO.getSendTo() != null) {				
				 SimpleMailMessage message = new SimpleMailMessage();
				 message.setTo( notificationDTO.getSendTo() );
				 message.setSubject(notificationDTO.getSubject());
				 message.setText(notificationDTO.getContent());
				 mailSender.send(message);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
