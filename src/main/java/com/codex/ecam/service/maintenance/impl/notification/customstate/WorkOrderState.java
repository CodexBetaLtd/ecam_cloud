package com.codex.ecam.service.maintenance.impl.notification.customstate;

import java.util.List;
import java.util.stream.Collectors;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;
import com.codex.ecam.service.maintenance.impl.notification.custom.EmailNotificationSubject;

public class WorkOrderState {

	EmailNotificationSubject emailNotificationSubject = null;
	EmailAndNotificationSender emailAndNotificationSender;


	public void setNotificationStateType(WorkOrderDTO workOrderDTO, EmailAndNotificationSender emailAndNotificationSender) {

		List<Integer> statusChangeNotifyUserList =
				workOrderDTO.getNotifications().stream()
				.filter(p -> p.getNotifyOnStatusChange() == Boolean.TRUE)
				.map(p -> p.getUserId())
				.collect(Collectors.toList());


		List<Integer> assignmentNotifyUserList =
				workOrderDTO.getNotifications().stream()
				.filter(p -> p.getNotifyOnAssignment() == Boolean.TRUE)
				.map(p -> p.getUserId())
				.collect(Collectors.toList());


		List<Integer> completionNotifyUserList =
				workOrderDTO.getNotifications().stream()
				.filter(p -> p.getNotifyOnCompletion() == Boolean.TRUE)
				.map(p -> p.getUserId())
				.collect(Collectors.toList());

		List<Integer> onlineOfflineNotifyUserList =
				workOrderDTO.getNotifications().stream()
				.filter(p -> p.getNotifyOnCompletion() == Boolean.TRUE)
				.map(p -> p.getUserId())
				.collect(Collectors.toList());

		List<Integer> taskCompletionNotifyUserList =
				workOrderDTO.getNotifications().stream()
				.filter(p -> p.getNotifyOnCompletion() == Boolean.TRUE)
				.map(p -> p.getUserId())
				.collect(Collectors.toList());

		workOrderDTO.setStatusChangeNotifyUserList(statusChangeNotifyUserList);
		workOrderDTO.setAssignmentNotifyUserList(assignmentNotifyUserList);
		workOrderDTO.setCompletionNotifyUserList(completionNotifyUserList);
		workOrderDTO.setOnlineOfflineNotifyUserList(onlineOfflineNotifyUserList);
		workOrderDTO.setTaskCompletionNotifyUserList(taskCompletionNotifyUserList);


		WorkOrderContext workOrderContext = new WorkOrderContext(workOrderDTO);
		emailNotificationSubject = new EmailNotificationSubject(workOrderDTO);
		this.emailAndNotificationSender = emailAndNotificationSender;


		if ((workOrderDTO.getId() == null) || (workOrderDTO.getWorkOrderStatus() != workOrderDTO.getPreviousWorkOrderStatus())) {
			workOrderContext.setState(new OnStatusChangeState(emailNotificationSubject, emailAndNotificationSender));
			workOrderContext.upDateStateAction();
			System.out.println("*** On State Change ***");
		}

		if (workOrderDTO.getWorkOrderStatus() == WorkOrderStatus.CLOSED) {
			workOrderContext.setState(new OnCompletionState(emailNotificationSubject, emailAndNotificationSender));
			workOrderContext.upDateStateAction();
			System.out.println("*** On WorkOrder Completion ***");
		} else {
			System.out.println("Existing Work Order");
		}


		//Todo: future modification
		/*
        workOrderContext.setState(new OnOnlineOfflineState(emailNotificationSubject,emailAndNotificationSender));
        workOrderContext.upDateStateAction();
        System.out.println("*** On Online Offline ***");
		 */

		workOrderContext.setState(new OnAssignmentState(emailNotificationSubject, emailAndNotificationSender));
		workOrderContext.upDateStateAction();
		System.out.println("*** On Completion ***");


	}


}
