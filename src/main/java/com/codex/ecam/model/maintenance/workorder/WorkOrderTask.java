package com.codex.ecam.model.maintenance.workorder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.listeners.workorder.WorkOrderTaskLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.task.TaskGroup;

@Entity
@Table(name="tbl_wo_task")
@EntityListeners( WorkOrderTaskLogListener.class )
public class WorkOrderTask extends BaseModel{

	private static final long serialVersionUID = -5896662579873256576L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="wo_task_s")
	@SequenceGenerator(name="wo_task_s", sequenceName="wo_task_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name = "work_order_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	@JoinColumn(name = "scheduled_maintenance_task_id")
	@ManyToOne(targetEntity = ScheduledMaintenanceTask.class, fetch = FetchType.LAZY)
	private ScheduledMaintenanceTask scheduledMaintenanceTask;

	@JoinColumn(name = "task_group_id")
	@ManyToOne(targetEntity = TaskGroup.class, fetch = FetchType.LAZY)
	private TaskGroup taskGroup;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "completion_note")
	private String completionNote;

	@JoinColumn(name = "assigned_user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User assignedUser;

	@Column(name="started_date")
	@Temporal(TemporalType.DATE)
	private Date startedDate;

	@Column(name = "estimated_hours")
	private BigDecimal estimatedHours;

	@JoinColumn(name = "completed_user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User completedUser;

	@Column(name="completed_date")
	@Temporal(TemporalType.DATE)
	private Date completedDate;

	@Column(name = "spent_hours")
	private BigDecimal spentHours;

	@Column(name = "completion_remark")
	private String completionRemark;

	@Column(name = "task_type_id")
	private TaskType taskType;

	@OneToMany(mappedBy = "workOrderTask", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderPart> workOrderParts;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public TaskGroup getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(TaskGroup taskGroup) {
		this.taskGroup = taskGroup;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompletionNote() {
		return completionNote;
	}

	public void setCompletionNote(String completionNote) {
		this.completionNote = completionNote;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public BigDecimal getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public User getCompletedUser() {
		return completedUser;
	}

	public void setCompletedUser(User completedUser) {
		this.completedUser = completedUser;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public BigDecimal getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(BigDecimal spentHours) {
		this.spentHours = spentHours;
	}

	public String getCompletionRemark() {
		return completionRemark;
	}

	public void setCompletionRemark(String completionRemark) {
		this.completionRemark = completionRemark;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Set<WorkOrderPart> getWorkOrderParts() {
		return workOrderParts;
	}

	public void setWorkOrderParts(Set<WorkOrderPart> workOrderParts) {
		this.workOrderParts = workOrderParts;
	}

	public ScheduledMaintenanceTask getScheduledMaintenanceTask() {
		return scheduledMaintenanceTask;
	}

	public void setScheduledMaintenanceTask(ScheduledMaintenanceTask scheduledMaintenanceTask) {
		this.scheduledMaintenanceTask = scheduledMaintenanceTask;
	}
}
