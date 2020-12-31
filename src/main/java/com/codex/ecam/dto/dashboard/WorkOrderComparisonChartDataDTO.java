package com.codex.ecam.dto.dashboard;

public class WorkOrderComparisonChartDataDTO {
	 
	private Integer previousWeekOpenWo;
	private Integer previousWeekClosedWo;
	
	private Integer currentWeekOpenWo;
	private Integer currentWeekClosedWo;
	
	private Integer nextWeekOpenWo;
	private Integer nextWeekClosedWo; 
	
	private Integer allCompletedWo;
	private Integer allOnTimeCompletedWo; 

	public Integer getPreviousWeekOpenWo() {
		return previousWeekOpenWo;
	}

	public void setPreviousWeekOpenWo(Integer previousWeekOpenWo) {
		this.previousWeekOpenWo = previousWeekOpenWo;
	}

	public Integer getPreviousWeekClosedWo() {
		return previousWeekClosedWo;
	}

	public void setPreviousWeekClosedWo(Integer previousWeekClosedWo) {
		this.previousWeekClosedWo = previousWeekClosedWo;
	}

	public Integer getCurrentWeekOpenWo() {
		return currentWeekOpenWo;
	}

	public void setCurrentWeekOpenWo(Integer currentWeekOpenWo) {
		this.currentWeekOpenWo = currentWeekOpenWo;
	}

	public Integer getCurrentWeekClosedWo() {
		return currentWeekClosedWo;
	}

	public void setCurrentWeekClosedWo(Integer currentWeekClosedWo) {
		this.currentWeekClosedWo = currentWeekClosedWo;
	}

	public Integer getNextWeekOpenWo() {
		return nextWeekOpenWo;
	}

	public void setNextWeekOpenWo(Integer nextWeekOpenWo) {
		this.nextWeekOpenWo = nextWeekOpenWo;
	}

	public Integer getNextWeekClosedWo() {
		return nextWeekClosedWo;
	}

	public void setNextWeekClosedWo(Integer nextWeekClosedWo) {
		this.nextWeekClosedWo = nextWeekClosedWo;
	}

	public Integer getAllCompletedWo() {
		return allCompletedWo;
	}

	public void setAllCompletedWo(Integer allCompletedWo) {
		this.allCompletedWo = allCompletedWo;
	}

	public Integer getAllOnTimeCompletedWo() {
		return allOnTimeCompletedWo;
	}

	public void setAllOnTimeCompletedWo(Integer allOnTimeCompletedWo) {
		this.allOnTimeCompletedWo = allOnTimeCompletedWo;
	}
	
	

}
