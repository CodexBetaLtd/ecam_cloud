package com.codex.ecam.listeners.notification;

import javax.persistence.PostPersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.exception.setting.notification.NotificationException;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.service.notification.api.NotificationService;


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
//            if (entity.getNotificationType().compareTo(NotificationType.INBOX_NOTIFICATION) == 0 && entity.getReceiver().getEmailNotification()) {
//                notificationService.fireInboxNotification(entity);
//            }
        } catch (Exception e) {
            throw new NotificationException();
        }

    }

}
