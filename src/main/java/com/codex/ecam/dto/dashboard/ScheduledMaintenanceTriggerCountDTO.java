package com.codex.ecam.dto.dashboard; 

import com.codex.ecam.dto.BaseDTO;

public class ScheduledMaintenanceTriggerCountDTO extends BaseDTO {

	private Integer smOutDatedCount;
	private Integer smCurrentWeekCount;
	private Integer smNextWeekCount; 
	private Integer smNextMonthCount; 
	
	private String smOutDatedFromDate;
	private String smOutDatedToDate;
	
	private String smCurrentWeekFromDate;
	private String smCurrentWeekToDate;
	
	private String smNextWeekFromDate;
	private String smNextWeekToDate; 
	
	private String smNextMonthFromDate;
	private String smNextMonthToDate; 

	public Integer getSmOutDatedCount() {
		return smOutDatedCount;
	}

	public void setSmOutDatedCount(Integer smOutDatedCount) {
		this.smOutDatedCount = smOutDatedCount;
	}

	public Integer getSmCurrentWeekCount() {
		return smCurrentWeekCount;
	}

	public void setSmCurrentWeekCount(Integer smCurrentWeekCount) {
		this.smCurrentWeekCount = smCurrentWeekCount;
	}

	public Integer getSmNextWeekCount() {
		return smNextWeekCount;
	}

	public void setSmNextWeekCount(Integer smNextWeekCount) {
		this.smNextWeekCount = smNextWeekCount;
	} 

	public Integer getSmNextMonthCount() {
		return smNextMonthCount;
	}

	public void setSmNextMonthCount(Integer smNextMonthCount) {
		this.smNextMonthCount = smNextMonthCount;
	} 

	public String getSmOutDatedFromDate() {
		return smOutDatedFromDate;
	}

	public void setSmOutDatedFromDate(String smOutDatedFromDate) {
		this.smOutDatedFromDate = smOutDatedFromDate;
	}

	public String getSmOutDatedToDate() {
		return smOutDatedToDate;
	}

	public void setSmOutDatedToDate(String smOutDatedToDate) {
		this.smOutDatedToDate = smOutDatedToDate;
	}

	public String getSmCurrentWeekFromDate() {
		return smCurrentWeekFromDate;
	}

	public void setSmCurrentWeekFromDate(String smCurrentWeekFromDate) {
		this.smCurrentWeekFromDate = smCurrentWeekFromDate;
	}

	public String getSmCurrentWeekToDate() {
		return smCurrentWeekToDate;
	}

	public void setSmCurrentWeekToDate(String smCurrentWeekToDate) {
		this.smCurrentWeekToDate = smCurrentWeekToDate;
	}

	public String getSmNextWeekFromDate() {
		return smNextWeekFromDate;
	}

	public void setSmNextWeekFromDate(String smNextWeekFromDate) {
		this.smNextWeekFromDate = smNextWeekFromDate;
	}

	public String getSmNextWeekToDate() {
		return smNextWeekToDate;
	}

	public void setSmNextWeekToDate(String smNextWeekToDate) {
		this.smNextWeekToDate = smNextWeekToDate;
	} 

	public String getSmNextMonthFromDate() {
		return smNextMonthFromDate;
	}

	public void setSmNextMonthFromDate(String smNextMonthFromDate) {
		this.smNextMonthFromDate = smNextMonthFromDate;
	}

	public String getSmNextMonthToDate() {
		return smNextMonthToDate;
	}

	public void setSmNextMonthToDate(String smNextMonthToDate) {
		this.smNextMonthToDate = smNextMonthToDate;
	}
 
}
