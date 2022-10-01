package com.codex.ecam.service.maintenance.api;


public interface EmailAndNotificationSender {

    void sendMail(Integer userId, String subject, String msg);

    void sendNotification(Integer userId, String subject, String msg);

}
