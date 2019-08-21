package com.neolith.focus.service.maintenance.impl.notification.customstate;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.service.maintenance.api.EmailAndNotificationSender;
import com.neolith.focus.service.maintenance.impl.notification.custom.EmailNotificationSubject;
import com.neolith.focus.service.maintenance.impl.notification.custom.OnAssignmentObserver;

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
