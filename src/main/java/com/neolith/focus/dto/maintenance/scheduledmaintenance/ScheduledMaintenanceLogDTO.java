package com.neolith.focus.dto.maintenance.scheduledmaintenance;

import java.util.Date;

import com.neolith.focus.constants.LogType;
import com.neolith.focus.dto.BaseDTO;

public class ScheduledMaintenanceLogDTO extends BaseDTO {

	private Integer id;
	private String assetName;
	private Integer assetId;
	private Integer ScheduledMaintenanceId;
	private String ScheduledMaintenanceCode;
	private LogType logType;
	private String notes;

	private Date createdDate;
	private String userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getScheduledMaintenanceId() {
		return ScheduledMaintenanceId;
	}

	public void setScheduledMaintenanceId(Integer scheduledMaintenanceId) {
		ScheduledMaintenanceId = scheduledMaintenanceId;
	}

	public String getScheduledMaintenanceCode() {
		return ScheduledMaintenanceCode;
	}

	public void setScheduledMaintenanceCode(String scheduledMaintenanceCode) {
		ScheduledMaintenanceCode = scheduledMaintenanceCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

}
