package com.codex.ecam.constants;

public enum NotificationType {

    INBOX_NOTIFICATION(1, "Inbox"),
    OUTBOX_NOTIFICATION(2, "Outbox"),
    TRASH_NOTIFICATION(3, "Trash");

    private Integer id;
    private String name;

    NotificationType(Integer id, String name){
        setId(id);
        setName(name);
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

}
