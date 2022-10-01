package com.codex.ecam.service.maintenance.impl.notification.custom;

import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;

public class OnAssignmentObserver implements EmailNotificationObserver {

	EmailAndNotificationSender emailAndNotificationSender;

	public OnAssignmentObserver(EmailAndNotificationSender emailAndNotificationSender) {
		this.emailAndNotificationSender = emailAndNotificationSender;
	}

	@Override
	public void update(WorkOrderDTO workOrderDTO) {
		onAssignment(workOrderDTO);
	}

	public void onAssignment(WorkOrderDTO workOrderDTO) {
		try {
			String subject = "Work Order Assinged "+workOrderDTO.getCode();
			String message = " Work Order created for ";
			int i=0;
			for(AssetDTO assetDTO:workOrderDTO.getAssets()){
				message+=assetDTO.getName()+"("+assetDTO.getName()+")";
				if(i<workOrderDTO.getAssets().size()){
					message+=",";
				}else{
					message+="..";
				}
				i++;
			}

			for (Integer userId : workOrderDTO.getAssignmentNotifyUserList()) {

				emailAndNotificationSender.sendMail(userId, subject, message);
				emailAndNotificationSender.sendNotification(userId, subject, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}

}
