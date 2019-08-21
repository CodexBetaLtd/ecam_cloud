package com.neolith.focus.dto.maintenance.scheduledmaintenance;

import com.neolith.focus.dto.BaseDTO;

public class ScheduledMaintenanceActivityLogDTO extends BaseDTO {
	
	private Integer id;
	private Integer workOrderId;
	private Integer userId;
	private Integer assetEventId;
	private Integer workOrderMeterReadingId;
	private Integer activityLogId;
	private String triggerDescription;
	private Integer triggerThresholdValue;
	
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAssetEventId() {
		return assetEventId;
	}
	public void setAssetEventId(Integer assetEventId) {
		this.assetEventId = assetEventId;
	}
	public Integer getWorkOrderMeterReadingId() {
		return workOrderMeterReadingId;
	}
	public void setWorkOrderMeterReadingId(Integer workOrderMeterReadingId) {
		this.workOrderMeterReadingId = workOrderMeterReadingId;
	}
	public Integer getActivityLogId() {
		return activityLogId;
	}
	public void setActivityLogId(Integer activityLogId) {
		this.activityLogId = activityLogId;
	}
	public String getTriggerDescription() {
		return triggerDescription;
	}
	public void setTriggerDescription(String triggerDescription) {
		this.triggerDescription = triggerDescription;
	}
	public Integer getTriggerThresholdValue() {
		return triggerThresholdValue;
	}
	public void setTriggerThresholdValue(Integer triggerThresholdValue) {
		this.triggerThresholdValue = triggerThresholdValue;
	}

}
