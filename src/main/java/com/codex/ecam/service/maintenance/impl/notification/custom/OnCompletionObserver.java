package com.codex.ecam.service.maintenance.impl.notification.custom;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;

public class OnCompletionObserver implements EmailNotificationObserver {

	EmailAndNotificationSender emailAndNotificationSender;

	public OnCompletionObserver(EmailAndNotificationSender emailAndNotificationSender) {
		this.emailAndNotificationSender = emailAndNotificationSender;
	}

	@Override
	public void update(WorkOrderDTO workOrderDTO) {
		if (workOrderDTO.getNotifications().size() > 0) {
			onCompletion(workOrderDTO);
		}
	}

	public void onCompletion(WorkOrderDTO workOrderDTO) {
		try {
			String subject = "Work Order Completion";
			String message = " Work Order Completion " + workOrderDTO.getCode();
			for (Integer userId : workOrderDTO.getCompletionNotifyUserList()) {
				emailAndNotificationSender.sendMail(userId, subject, message);
				emailAndNotificationSender.sendNotification(userId, subject, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}

}
