package com.neolith.focus.model.maintenance.scheduledmaintenance;

import java.math.BigDecimal;
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
import javax.persistence.Transient;

import com.neolith.focus.constants.TaskType;
import com.neolith.focus.listeners.scheduledmaintenance.ScheduledMaintenanceTaskLogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.model.maintenance.task.TaskGroup;
import com.neolith.focus.model.maintenance.workorder.WorkOrderTask;

@Entity
@Table(name = "tbl_scheduled_maintenance_task")
@EntityListeners( ScheduledMaintenanceTaskLogListener.class )
public class ScheduledMaintenanceTask extends BaseModel {

	private static final long serialVersionUID = 8911445877905171224L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_task_s")
	@SequenceGenerator(name = "scheduled_task_s", sequenceName = "scheduled_task_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "task_group_id")
	@ManyToOne(targetEntity = TaskGroup.class, fetch = FetchType.LAZY)
	private TaskGroup taskGroup;

	@JoinColumn(name = "scheduled_maintenance_trigger_id")
	@ManyToOne(targetEntity = ScheduledMaintenanceTrigger.class, fetch = FetchType.LAZY)
	private ScheduledMaintenanceTrigger scheduledMaintenanceTrigger;

	@JoinColumn(name = "assigned_user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User assignedUser;

	@Column(name = "task_type_id")
	private TaskType taskType;

	@Column(name = "description")
	private String description;

	@Column(name = "estimated_hours")
	private BigDecimal estimatedHours;

	@OneToMany(mappedBy = "scheduledMaintenanceTask", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenancePart> scheduledMaintenanceParts;

	@OneToMany(mappedBy = "scheduledMaintenanceTask", fetch = FetchType.LAZY)
	private Set<WorkOrderTask> workOrderTasks;

	@Transient
	private Integer taskIndex;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public TaskGroup getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(TaskGroup taskGroup) {
		this.taskGroup = taskGroup;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Set<ScheduledMaintenancePart> getScheduledMaintenanceParts() {
		return scheduledMaintenanceParts;
	}

	public void setScheduledMaintenanceParts(Set<ScheduledMaintenancePart> scheduledMaintenanceParts) {
		updateCollection("scheduledMaintenanceParts", scheduledMaintenanceParts);
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Integer getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(Integer taskIndex) {
		this.taskIndex = taskIndex;
	}

	public ScheduledMaintenanceTrigger getScheduledMaintenanceTrigger() {
		return scheduledMaintenanceTrigger;
	}

	public void setScheduledMaintenanceTrigger(ScheduledMaintenanceTrigger scheduledMaintenanceTrigger) {
		this.scheduledMaintenanceTrigger = scheduledMaintenanceTrigger;
	}

	public Set<WorkOrderTask> getWorkOrderTasks() {
		return workOrderTasks;
	}

	public void setWorkOrderTasks(Set<WorkOrderTask> workOrderTasks) {
		updateCollection("workOrderTasks", workOrderTasks);
	}
}
