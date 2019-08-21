package com.neolith.focus.service.maintenance.impl.notification.custom;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.service.maintenance.api.EmailAndNotificationSender;


public class OnStatusChangeObserver implements EmailNotificationObserver {

	EmailAndNotificationSender emailAndNotificationSender = null;

	public OnStatusChangeObserver(EmailAndNotificationSender emailAndNotificationSender) {
		this.emailAndNotificationSender = emailAndNotificationSender;
	}

	@Override
	public void update(WorkOrderDTO workOrderDTO) {
		if (workOrderDTO.getNotifications().size() > 0) {
			onStatusChange(workOrderDTO);
		}
	}


	public void onStatusChange(WorkOrderDTO workOrderDTO) {
		try {
			for (Integer userId : workOrderDTO.getStatusChangeNotifyUserList()) {
				String subject = "Work Order On Status Change";
				String message = " Work Order On Status Change Email Body";
				emailAndNotificationSender.sendMail(userId, subject, message);
				emailAndNotificationSender.sendNotification(userId, subject, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
