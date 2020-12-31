package com.codex.ecam.dto.biz.notification;

public class NotificationViewDTO {

    private Integer inboxItemCount;
    private Integer outboxItemCount;
    private Integer trashItemCount;


    public Integer getInboxItemCount() {
        return inboxItemCount;
    }

    public void setInboxItemCount(Integer inboxItemCount) {
        this.inboxItemCount = inboxItemCount;
    }

    public Integer getOutboxItemCount() {
        return outboxItemCount;
    }

    public void setOutboxItemCount(Integer outboxItemCount) {
        this.outboxItemCount = outboxItemCount;
    }

    public Integer getTrashItemCount() {
        return trashItemCount;
    }

    public void setTrashItemCount(Integer trashItemCount) {
        this.trashItemCount = trashItemCount;
    }
}
