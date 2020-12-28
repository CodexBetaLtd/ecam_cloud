package com.codex.ecam.service.maintenance.impl.notification.custom;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderNotificationDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;

public class OnTaskCompletedObserver implements EmailNotificationObserver {


	EmailAndNotificationSender emailAndNotificationSender;

	public OnTaskCompletedObserver(EmailAndNotificationSender emailAndNotificationSender) {
		this.emailAndNotificationSender = emailAndNotificationSender;
	}

	@Override
	public void update(WorkOrderDTO workOrderDTO) {
		onTaskCompleted(workOrderDTO);
	}


	public void onTaskCompleted(WorkOrderDTO workOrderDTO){
		try {
			            String subject = "Work Order Task Completed";
			            String message = " Work Order Task Completed Email Body";
			            for(WorkOrderNotificationDTO dto:workOrderDTO.getNotifications()){
				            emailAndNotificationSender.sendMail(dto.getUserId(),subject,message);
				            emailAndNotificationSender.sendNotification(dto.getUserId(),subject,message);
			            }
		}catch (Exception ex){
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}
}
