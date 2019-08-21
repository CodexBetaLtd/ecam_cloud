package com.neolith.focus.model.maintenance.scheduledmaintenance;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.User;

import javax.persistence.*;

@Entity
@Table(name = "tbl_scheduled_maintenance_user")
public class ScheduledMaintenanceUser extends BaseModel {

    private static final long serialVersionUID = -442586468701768607L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_maintenance_user_s")
    @SequenceGenerator(name = "scheduled_maintenance_user_s", sequenceName = "scheduled_maintenance_user_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "scheduled_maintenance_id")
    @ManyToOne(targetEntity = ScheduledMaintenance.class, fetch = FetchType.LAZY)
    private ScheduledMaintenance scheduledMaintenance;

    @Column(name = "can_update_or_close")
    private Boolean canUpdateOrClose;

    @Column(name = "notify_on_assignment")
    private Boolean notifyOnAssignment;

    @Column(name = "notify_on_completion")
    private Boolean notifyOnCompletion;

    @Column(name = "notify_on_online_offline")
    private Boolean notifyOnOnlineOffline;

    @Column(name = "notify_on_status_change")
    private Boolean notifyOnStatusChange;

    @Column(name = "notify_on_task_completed")
    private Boolean notifyOnTaskCompleted;

    @Column(name = "primary_technician")
    private Boolean primaryTechnician;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ScheduledMaintenance getScheduledMaintenance() {
        return scheduledMaintenance;
    }

    public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
        this.scheduledMaintenance = scheduledMaintenance;
    }

    public Boolean getCanUpdateOrClose() {
        return canUpdateOrClose;
    }

    public void setCanUpdateOrClose(Boolean canUpdateOrClose) {
        this.canUpdateOrClose = canUpdateOrClose;
    }

    public Boolean getNotifyOnAssignment() {
        return notifyOnAssignment;
    }

    public void setNotifyOnAssignment(Boolean notifyOnAssignment) {
        this.notifyOnAssignment = notifyOnAssignment;
    }

    public Boolean getNotifyOnCompletion() {
        return notifyOnCompletion;
    }

    public void setNotifyOnCompletion(Boolean notifyOnCompletion) {
        this.notifyOnCompletion = notifyOnCompletion;
    }

    public Boolean getNotifyOnOnlineOffline() {
        return notifyOnOnlineOffline;
    }

    public void setNotifyOnOnlineOffline(Boolean notifyOnOnlineOffline) {
        this.notifyOnOnlineOffline = notifyOnOnlineOffline;
    }

    public Boolean getNotifyOnStatusChange() {
        return notifyOnStatusChange;
    }

    public void setNotifyOnStatusChange(Boolean notifyOnStatusChange) {
        this.notifyOnStatusChange = notifyOnStatusChange;
    }

    public Boolean getNotifyOnTaskCompleted() {
        return notifyOnTaskCompleted;
    }

    public void setNotifyOnTaskCompleted(Boolean notifyOnTaskCompleted) {
        this.notifyOnTaskCompleted = notifyOnTaskCompleted;
    }

    public Boolean getPrimaryTechnician() {
        return primaryTechnician;
    }

    public void setPrimaryTechnician(Boolean primaryTechnician) {
        this.primaryTechnician = primaryTechnician;
    }

}
