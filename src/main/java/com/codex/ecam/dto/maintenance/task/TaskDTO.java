package com.codex.ecam.dto.maintenance.task;

import java.math.BigDecimal;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.BaseDTO;

public class TaskDTO extends BaseDTO {

	private Integer id;
	private Integer version;
	private String name;
	private String description;
	private BigDecimal estimatedHours;
	private TaskType taskType;
	private String taskTypeName;
	private Integer taskTypeId;
	private Integer assetCatgoryTaskId;
	private Integer assetCatgoryVersionId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
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

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Integer getAssetCatgoryTaskId() {
		return assetCatgoryTaskId;
	}

	public void setAssetCatgoryTaskId(Integer assetCatgoryTaskId) {
		this.assetCatgoryTaskId = assetCatgoryTaskId;
	}

	public Integer getAssetCatgoryVersionId() {
		return assetCatgoryVersionId;
	}

	public void setAssetCatgoryVersionId(Integer assetCatgoryVersionId) {
		this.assetCatgoryVersionId = assetCatgoryVersionId;
	}
	
	
}
