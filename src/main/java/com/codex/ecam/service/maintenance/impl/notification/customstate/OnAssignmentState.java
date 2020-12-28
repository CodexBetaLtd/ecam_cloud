package com.codex.ecam.service.maintenance.impl.notification.customstate;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;
import com.codex.ecam.service.maintenance.impl.notification.custom.EmailNotificationSubject;
import com.codex.ecam.service.maintenance.impl.notification.custom.OnAssignmentObserver;

public class OnAssignmentState implements State {

    EmailNotificationSubject emailNotificationSubject = null;
    EmailAndNotificationSender emailAndNotificationSender = null;
    OnAssignmentObserver onAssignmentObserver = null;

    public OnAssignmentState(EmailNotificationSubject emailNotificationSubject, EmailAndNotificationSender emailAndNotificationSender) {
        this.emailNotificationSubject = emailNotificationSubject;
        this.emailAndNotificationSender = emailAndNotificationSender;
        this.onAssignmentObserver = new OnAssignmentObserver(this.emailAndNotificationSender);
    }

    @Override
    public void stateAction(WorkOrderDTO workOrderDTO) {
        emailNotificationSubject = new EmailNotificationSubject(workOrderDTO);
        emailNotificationSubject.registerObserver(onAssignmentObserver);
        emailNotificationSubject.setWorkOrderDTO(workOrderDTO);
    }



}
