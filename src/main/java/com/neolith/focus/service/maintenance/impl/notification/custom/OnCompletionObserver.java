package com.neolith.focus.service.maintenance.impl.notification.custom;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.service.maintenance.api.EmailAndNotificationSender;

public class OnCompletionObserver implements EmailNotificationObserver {


	EmailAndNotificationSender emailAndNotificationSender;

	public OnCompletionObserver(EmailAndNotificationSender emailAndNotificationSender) {
		this.emailAndNotificationSender = emailAndNotificationSender;
	}

	@Override
	public void update(WorkOrderDTO workOrderDTO) {
		if(workOrderDTO.getNotifications().size()>0 ){
			onCompletion(workOrderDTO);
		}
	}


	public void onCompletion(WorkOrderDTO workOrderDTO){
		try {
			//			String email = "";
			//			String subject = "Work Order On Completion";
			//			String message = " Work Order On Completion Email Body";
			//            emailAndNotificationSender.sendMail(subject,message);
			//            emailAndNotificationSender.sendNotification(subject,message);
		}catch (Exception ex){
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}


}
