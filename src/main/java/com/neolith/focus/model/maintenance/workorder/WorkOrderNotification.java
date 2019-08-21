package com.neolith.focus.model.maintenance.workorder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.User;
@Entity
@Table(name="tbl_wo_notification")
public class WorkOrderNotification extends BaseModel {

	private static final long serialVersionUID = -3850310852386241291L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="wo_notification_s")
	@SequenceGenerator(name="wo_notification_s", sequenceName="wo_notification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name="work_order_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	@JoinColumn(name="user_id")
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private User user;

	@Column(name="notify_on_assignment")
	private Boolean notifyOnAssignment;

	@Column(name="notify_on_status_change")
	private Boolean notifyOnStatusChange;

	@Column(name="notify_on_completion")
	private Boolean notifyOnCompletion;

	@Column(name="notify_on_task_completed")
	private Boolean notifyOnTaskCompleted;

	@Column(name="notify_on_online_offline")
	private Boolean notifyOnOnlineOffline;


	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
