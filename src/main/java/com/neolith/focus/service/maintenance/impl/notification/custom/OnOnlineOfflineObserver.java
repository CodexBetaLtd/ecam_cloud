package com.neolith.focus.service.maintenance.impl.notification.custom;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.service.maintenance.api.EmailAndNotificationSender;

public class OnOnlineOfflineObserver implements EmailNotificationObserver {

    EmailAndNotificationSender emailAndNotificationSender;

    public OnOnlineOfflineObserver(EmailAndNotificationSender emailAndNotificationSender) {
        this.emailAndNotificationSender = emailAndNotificationSender;
    }

    @Override
    public void update(WorkOrderDTO workOrderDTO) {
        onOnlineOffline(workOrderDTO);
    }

    public void onOnlineOffline(WorkOrderDTO workOrderDTO) {
        try {
            for (Integer userId : workOrderDTO.getOnlineOfflineNotifyUserList()) {
                String subject = "Work Order Online Offline";
                String message = " Work Order  Online Offline Email Body";
                emailAndNotificationSender.sendMail(userId, subject, message);
                emailAndNotificationSender.sendNotification(userId, subject, message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }

    }
}
