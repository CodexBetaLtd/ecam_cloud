package com.codex.ecam.dto.maintenance.workOrder;

import com.codex.ecam.dto.BaseDTO;


public class WorkOrderNotificationDTO extends BaseDTO {

	private Integer id;
	private Integer userId;
    private String userName;
    private Integer workOrderId;
	private Boolean notifyOnAssignment;
	private Boolean notifyOnStatusChange;
	private Boolean notifyOnCompletion;
	private Boolean notifyOnTaskCompleted;
	private Boolean notifyOnOnlineOffline;  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Boolean getNotifyOnAssignment() {
        return notifyOnAssignment;
    }

    public void setNotifyOnAssignment(Boolean notifyOnAssignment) {
        this.notifyOnAssignment = notifyOnAssignment;
    }

    public Boolean getNotifyOnStatusChange() {
        return notifyOnStatusChange;
    }

    public void setNotifyOnStatusChange(Boolean notifyOnStatusChange) {
        this.notifyOnStatusChange = notifyOnStatusChange;
    }

    public Boolean getNotifyOnCompletion() {
        return notifyOnCompletion;
    }

    public void setNotifyOnCompletion(Boolean notifyOnCompletion) {
        this.notifyOnCompletion = notifyOnCompletion;
    }

    public Boolean getNotifyOnTaskCompleted() {
        return notifyOnTaskCompleted;
    }

    public void setNotifyOnTaskCompleted(Boolean notifyOnTaskCompleted) {
        this.notifyOnTaskCompleted = notifyOnTaskCompleted;
    }

    public Boolean getNotifyOnOnlineOffline() {
        return notifyOnOnlineOffline;
    }

    public void setNotifyOnOnlineOffline(Boolean notifyOnOnlineOffline) {
        this.notifyOnOnlineOffline = notifyOnOnlineOffline;
    }
}
