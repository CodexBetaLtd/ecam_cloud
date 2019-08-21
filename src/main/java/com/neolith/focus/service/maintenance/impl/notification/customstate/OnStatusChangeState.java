package com.neolith.focus.service.maintenance.impl.notification.customstate;


import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.service.maintenance.api.EmailAndNotificationSender;
import com.neolith.focus.service.maintenance.impl.notification.custom.EmailNotificationSubject;
import com.neolith.focus.service.maintenance.impl.notification.custom.OnStatusChangeObserver;

public class OnStatusChangeState implements State {

    EmailNotificationSubject emailNotificationSubject = null;
    EmailAndNotificationSender emailAndNotificationSender = null;
    OnStatusChangeObserver onStatusChangeObserver = null;

    public OnStatusChangeState(EmailNotificationSubject emailNotificationSubject, EmailAndNotificationSender emailAndNotificationSender) {
        this.emailNotificationSubject = emailNotificationSubject;
        this.emailAndNotificationSender = emailAndNotificationSender;
        this.onStatusChangeObserver = new OnStatusChangeObserver(this.emailAndNotificationSender);
    }


    @Override
    public void stateAction(WorkOrderDTO workOrderDTO) {
        emailNotificationSubject = new EmailNotificationSubject(workOrderDTO);
        emailNotificationSubject.registerObserver(onStatusChangeObserver);
        emailNotificationSubject.setWorkOrderDTO(workOrderDTO);
    }


}
