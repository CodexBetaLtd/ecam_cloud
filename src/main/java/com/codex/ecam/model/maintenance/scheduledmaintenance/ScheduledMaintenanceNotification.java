package com.codex.ecam.model.maintenance.scheduledmaintenance;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name = "tbl_scheduled_maintenance_notification")
public class ScheduledMaintenanceNotification extends BaseModel {

    private static final long serialVersionUID = -3850310852386241291L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_maintenance_notification_s")
    @SequenceGenerator(name = "scheduled_maintenance_notification_s", sequenceName = "scheduled_maintenance_notification_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "scheduled_maintenance_id")
    @ManyToOne(targetEntity = ScheduledMaintenance.class, fetch = FetchType.LAZY)
    private ScheduledMaintenance scheduledMaintenance;

    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "notify_on_assignment")
    private Boolean notifyOnAssignment;

    @Column(name = "notify_on_status_change")
    private Boolean notifyOnStatusChange;

    @Column(name = "notify_on_completion")
    private Boolean notifyOnCompletion;

    @Column(name = "notify_on_task_completed")
    private Boolean notifyOnTaskCompleted;

    @Column(name = "notify_on_online_offline")
    private Boolean notifyOnOnlineOffline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ScheduledMaintenance getScheduledMaintenance() {
        return scheduledMaintenance;
    }

    public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
        this.scheduledMaintenance = scheduledMaintenance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
