package com.codex.ecam.service.inventory.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.service.inventory.api.PartNotificationService;
import com.codex.ecam.service.notification.api.NotificationRecipientBuilderService;
import com.codex.ecam.support.notification.NotificationInternalSender;
import com.codex.ecam.util.VelocityEmailSender;

@Service
public class PartNotificationServiceImpl implements PartNotificationService {

	@Autowired
	private NotificationRecipientBuilderService notificationRecipientBuilderService;
	
	@Autowired
	private VelocityEmailSender velocityEmailService;
	
	@Autowired
	private NotificationInternalSender notificationInternalSender;
	
	@Autowired
	private UserDao userDao;  

	@Override
	public void publishStockAddNotifications(Stock domain) throws Exception {
		List<Integer> recipients = notificationRecipientBuilderService.getStockAddNotifyRecipients(domain); 
		String subject="Stock Added to Part [ Code: " + domain.getPart().getCode() + " ]";
		sendNotifications(subject,domain,recipients);
	}
	
	@Override
	public void publishStockRemoveNotifications(Stock domain) throws Exception {
		List<Integer> recipients = notificationRecipientBuilderService.getStockRemoveNotifyRecipients(domain);
		String subject="Stock has removed [ Batch No: " + domain.getBatchNo() + "]";
		sendNotifications(subject,domain,recipients);
	}
	
	private void sendNotifications(String subject,Stock domain, List<Integer> recipients) {
		for (Integer recipientId : recipients) { 
			NotificationDTO notificationDTO = buildNotification(recipientId, subject, generateNotificationMessage(domain)); 
			velocityEmailService.sendEmail(generateEmailMessage(domain,notificationDTO));
			notificationInternalSender.send(notificationDTO);
		}
	}
	
	private VelocityMail generateEmailMessage(Stock domain,NotificationDTO notificationDTO){
		VelocityMail mail=new VelocityMail();
		mail.getModel().put("itemName",  domain.getPart().getName());
		mail.getModel().put("itemCode",domain.getPart().getCode() );
		mail.getModel().put("batchNo",domain.getBatchNo()); 
		mail.getModel().put("qtyOnHand",domain.getCurrentQuantity());
		mail.getModel().put("minQty",domain.getMinQuantity());
		mail.getModel().put("mailTopic", notificationDTO.getSubject());
		mail.getModel().put("user",notificationDTO.getReceiverName());
		mail.setTo(notificationDTO.getSendTo());
		mail.setSubject( notificationDTO.getSubject());
		mail.setVmTemplate("stocknotification");
		return mail;	
	}

	private String generateNotificationMessage(Stock domain){
		String message = "<h3><strong>Item Name: " + domain.getPart().getName() + "</strong></h3><br>"
				+ "<p>Item Code: " +  domain.getPart().getCode() + "</p>"
				+ "<p>Batch No: " +  domain.getBatchNo() + "</p>"
				+ "<p>Quantity On Hand : " +  domain.getCurrentQuantity() + "</p>"
				+ "<p>Min Quantity : " + domain.getMinQuantity() +"</p>";
		return message;	
	}

	private NotificationDTO buildNotification(Integer recipientId, String subject, String message) {
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setSubject(subject);
		notificationDTO.setContent(message);
		notificationDTO.setSystemMessage(Boolean.TRUE);
		notificationDTO.setReceivedUserId(recipientId);  
		setRecipientData(recipientId, notificationDTO);
		return notificationDTO;
	}
 
	private void setRecipientData(Integer recipientId, NotificationDTO dto) { 
		User recipient = userDao.findOne(recipientId);
        if (recipient.getEmailNotification() == true) {
            if (recipient.getEmailAddress() != null) {
				dto.setSendTo( recipient.getEmailAddress() );
			}
		}  
		dto.setBusinessId(recipient.getBusiness().getId()); 
	}

}
