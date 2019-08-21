package com.neolith.focus.listeners.notification;

import javax.persistence.PostPersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.constants.NotificationType;
import com.neolith.focus.exception.setting.notification.NotificationException;
import com.neolith.focus.model.biz.notification.Notification;
import com.neolith.focus.service.notification.api.NotificationService;


public class NotificationFireListener {

    @Autowired
    private NotificationService notificationService;

    @PostPersist
    public void fireNotification(Notification entity) throws Exception {
        AutowireHelper.autowire(this, notificationService);
//        NotificationResult result = notificationService.findNotificationById(entity.getId());
        fireInboxNotification(entity);
    }


    private void fireInboxNotification(Notification entity) throws Exception {
        try {
            if (entity.getNotificationType().compareTo(NotificationType.INBOX_NOTIFICATION) == 0 && entity.getReceiver().getEmailNotification()) {
                notificationService.fireInboxNotification(entity);
            }
        } catch (Exception e) {
            throw new NotificationException();
        }

    }

}
