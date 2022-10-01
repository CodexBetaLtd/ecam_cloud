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
		if (previousWeekOpenWo == null) {
			return 0;
		}
		return previousWeekOpenWo;
	}

	public void setPreviousWeekOpenWo(Integer previousWeekOpenWo) {
		this.previousWeekOpenWo = previousWeekOpenWo;
	}

	public Integer getPreviousWeekClosedWo() {
		if (previousWeekClosedWo == null) {
			return 0;
		}
		return previousWeekClosedWo;
	}

	public void setPreviousWeekClosedWo(Integer previousWeekClosedWo) {
		this.previousWeekClosedWo = previousWeekClosedWo;
	}

	public Integer getCurrentWeekOpenWo() {
		if (currentWeekOpenWo == null) {
			return 0;
		}
		return currentWeekOpenWo;
	}

	public void setCurrentWeekOpenWo(Integer currentWeekOpenWo) {
		this.currentWeekOpenWo = currentWeekOpenWo;
	}

	public Integer getCurrentWeekClosedWo() {
		if (currentWeekClosedWo == null) {
			return 0;
		}
		return currentWeekClosedWo;
	}

	public void setCurrentWeekClosedWo(Integer currentWeekClosedWo) {
		this.currentWeekClosedWo = currentWeekClosedWo;
	}

	public Integer getNextWeekOpenWo() {
		if (nextWeekOpenWo == null) {
			return 0;
		}
		return nextWeekOpenWo;
	}

	public void setNextWeekOpenWo(Integer nextWeekOpenWo) {
		this.nextWeekOpenWo = nextWeekOpenWo;
	}

	public Integer getNextWeekClosedWo() {
		if (nextWeekClosedWo == null) {
			return 0;
		}
		return nextWeekClosedWo;
	}

	public void setNextWeekClosedWo(Integer nextWeekClosedWo) {
		this.nextWeekClosedWo = nextWeekClosedWo;
	}

	public Integer getAllCompletedWo() {
		if (allCompletedWo == null) {
			return 0;
		}
		return allCompletedWo;
	}

	public void setAllCompletedWo(Integer allCompletedWo) {
		this.allCompletedWo = allCompletedWo;
	}

	public Integer getAllOnTimeCompletedWo() {
		if (allOnTimeCompletedWo == null) {
			return 0;
		}
		return allOnTimeCompletedWo;
	}

	public void setAllOnTimeCompletedWo(Integer allOnTimeCompletedWo) {
		this.allOnTimeCompletedWo = allOnTimeCompletedWo;
	}



}
