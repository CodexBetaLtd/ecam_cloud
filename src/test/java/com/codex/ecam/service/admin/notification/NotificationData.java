package com.codex.ecam.service.admin.notification;

import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.model.biz.notification.Notification;

/**
 * Created by neo89 on 12/20/16.
 */
public class NotificationData {

    private static NotificationData instance = new NotificationData();

    private NotificationData() {
    }

    public static NotificationData getInstance() {
        return instance;
    }

    public NotificationDTO getDummyNotificationDTO(){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setSubject("Test-Notification-Subject");
        notificationDTO.setContent("Test-Notification-Content");
        notificationDTO.setIsOpen(Boolean.FALSE);
        return notificationDTO;
    } 

    public Notification getDummyNotification(){
        Notification notification = new Notification();
        notification.setSubject("Test-Notification-DOMAIN-Subject");
        notification.setContent("Test-Notification-DOMAIN-Content");
        notification.setIsOpen(Boolean.FALSE);
        return notification;
    } 

    protected NotificationDTO notificationDataSet01(){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setSubject("TestSubjectOne");
        notificationDTO.setContent("TestContentOne");
        notificationDTO.setIsOpen(Boolean.FALSE);
        return notificationDTO;
    }

    protected NotificationDTO notificationDataSet02(){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setSubject("TestSubjectTwo");
        notificationDTO.setContent("TestContentTwo");
        notificationDTO.setIsOpen(Boolean.FALSE);
        return notificationDTO;
    }

    protected NotificationDTO notificationDataSet03(){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setSubject("TestSubjectThree");
        notificationDTO.setContent("TestContentThree");
        notificationDTO.setIsOpen(Boolean.FALSE);
        return notificationDTO;
    }



}
