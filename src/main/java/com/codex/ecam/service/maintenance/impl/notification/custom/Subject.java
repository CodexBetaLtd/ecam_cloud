package com.codex.ecam.service.maintenance.impl.notification.custom;

public interface Subject {

    void registerObserver(EmailNotificationObserver emailNotificationObserver);

    void removeObserver(EmailNotificationObserver emailNotificationObserver);

    void notifyObservers();

}
