package com.codex.ecam.service.maintenance.impl.notification.customstate;


import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;
import com.codex.ecam.service.maintenance.impl.notification.custom.EmailNotificationSubject;
import com.codex.ecam.service.maintenance.impl.notification.custom.OnCompletionObserver;

public class OnOnlineOfflineState implements State {


    EmailNotificationSubject emailNotificationSubject = null;
    EmailAndNotificationSender emailAndNotificationSender = null;
    OnCompletionObserver onCompletionObserver = null;

    public OnOnlineOfflineState(EmailNotificationSubject emailNotificationSubject, EmailAndNotificationSender emailAndNotificationSender) {
        this.emailNotificationSubject = emailNotificationSubject;
        this.emailAndNotificationSender = emailAndNotificationSender;
        this.onCompletionObserver = new OnCompletionObserver(this.emailAndNotificationSender);
    }

    @Override
    public void stateAction(WorkOrderDTO workOrderDTO) {
        emailNotificationSubject = new EmailNotificationSubject(workOrderDTO);
        emailNotificationSubject.registerObserver(onCompletionObserver);
        emailNotificationSubject.setWorkOrderDTO(workOrderDTO);
    }


}
