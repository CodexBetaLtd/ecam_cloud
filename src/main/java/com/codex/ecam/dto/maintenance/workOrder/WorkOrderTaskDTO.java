package com.codex.ecam.dto.maintenance.workOrder;

import java.math.BigDecimal;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.BaseDTO;

public class WorkOrderTaskDTO extends BaseDTO {

	private Integer id;
	private Integer index;
	private Integer workOrderId;
	private Integer taskGroupId;
	private String name;
	private String description;

	private Integer assignedAssetId;
	private String assignedAssetName;

	private Integer taskTypeId;
	private String taskTypeDescription;
	private TaskType taskType;

	private Integer assignedUserId;
	private String assignedUserName;
	private String startedDate;
	private BigDecimal estimatedHours;

	private Integer completedUserId;
	private String completedUserName;
	private String completedDate;
	private BigDecimal spentHours;

	private String completionNote;
	private String completionRemark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Integer getTaskGroupId() {
		return taskGroupId;
	}

	public void setTaskGroupId(Integer taskGroupId) {
		this.taskGroupId = taskGroupId;
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

	public Integer getAssignedAssetId() {
		return assignedAssetId;
	}

	public void setAssignedAssetId(Integer assignedAssetId) {
		this.assignedAssetId = assignedAssetId;
	}

	public Integer getAssignedUserId() {
		return assignedUserId;
	}

	public void setAssignedUserId(Integer assignedUserId) {
		this.assignedUserId = assignedUserId;
	}

	public String getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}

	public BigDecimal getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Integer getCompletedUserId() {
		return completedUserId;
	}

	public void setCompletedUserId(Integer completedUserId) {
		this.completedUserId = completedUserId;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public BigDecimal getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(BigDecimal spentHours) {
		this.spentHours = spentHours;
	}

	public String getCompletionNote() {
		return completionNote;
	}

	public void setCompletionNote(String completionNote) {
		this.completionNote = completionNote;
	}

	public String getCompletionRemark() {
		return completionRemark;
	}

	public void setCompletionRemark(String completionRemark) {
		this.completionRemark = completionRemark;
	}

	public String getAssignedAssetName() {
		return assignedAssetName;
	}

	public void setAssignedAssetName(String assignedAssetName) {
		this.assignedAssetName = assignedAssetName;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public String getTaskTypeDescription() {
		return taskTypeDescription;
	}

	public void setTaskTypeDescription(String taskTypeDescription) {
		this.taskTypeDescription = taskTypeDescription;
	}

	public String getAssignedUserName() {
		return assignedUserName;
	}

	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}

	public String getCompletedUserName() {
		return completedUserName;
	}

	public void setCompletedUserName(String completedUserName) {
		this.completedUserName = completedUserName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
}
