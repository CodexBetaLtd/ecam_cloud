package com.neolith.focus.service.maintenance.impl.notification.custom;

public interface Subject {

    void registerObserver(EmailNotificationObserver emailNotificationObserver);

    void removeObserver(EmailNotificationObserver emailNotificationObserver);

    void notifyObservers();

}
