package com.codex.ecam.constants.user;

import java.util.ArrayList;
import java.util.List;

public enum NotificationCategory {

    EMAIL_NOTIFICATION(0, "Email Notification"),
    EMAIL_SYSTEM_ERROR(1, "Email System Error"),
    //    EMAIL_WELCOME(1, "Email Welcome"),
    EMAIL_WELCOME(1, "Email Welcome"),
    MADAM(1, "Madam");


    private Integer id;
    private String name;

    NotificationCategory(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<NotificationCategory> getSalutations() {
        List<NotificationCategory> list = new ArrayList<NotificationCategory>();
        list.add(NotificationCategory.EMAIL_NOTIFICATION);
        list.add(NotificationCategory.MADAM);
        return list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
