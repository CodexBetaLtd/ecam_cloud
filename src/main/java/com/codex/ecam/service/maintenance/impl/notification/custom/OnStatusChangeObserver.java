package com.codex.ecam.service.maintenance.impl.notification.custom;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;


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
				String subject = "Work Order Status Change "+workOrderDTO.getCode();
				String message = " Work Order Status Change from "+ workOrderDTO.getPreviousWorkOrderStatus().getName()+" to "+workOrderDTO.getWorkOrderStatus().getName() ;
				emailAndNotificationSender.sendMail(userId, subject, message);
				emailAndNotificationSender.sendNotification(userId, subject, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
