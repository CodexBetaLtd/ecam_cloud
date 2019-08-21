package com.codex.ecam.dto.maintenance.scheduledmaintenance;

import java.math.BigDecimal;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.BaseDTO;

public class ScheduledMaintenanceTaskDTO extends BaseDTO {

	private Integer id;
	private Integer index;
	private Integer scheduledMaintenanceId;
	private Integer taskGroupId;
	private String description;
	private BigDecimal estimatedHours;
	private TaskType taskType;

	private Integer assetId;
	private String assetName;

	private Integer userId;
	private String userName;

	private Integer triggerId;
	private Integer triggerIndex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScheduledMaintenanceId() {
		return scheduledMaintenanceId;
	}

	public void setScheduledMaintenanceId(Integer scheduledMaintenanceId) {
		this.scheduledMaintenanceId = scheduledMaintenanceId;
	}

	public Integer getTaskGroupId() {
		return taskGroupId;
	}

	public void setTaskGroupId(Integer taskGroupId) {
		this.taskGroupId = taskGroupId;
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

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Integer getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}

	public Integer getTriggerIndex() {
		return triggerIndex;
	}

	public void setTriggerIndex(Integer triggerIndex) {
		this.triggerIndex = triggerIndex;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
