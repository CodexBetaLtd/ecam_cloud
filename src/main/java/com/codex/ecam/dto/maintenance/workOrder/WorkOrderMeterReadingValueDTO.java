package com.codex.ecam.dto.maintenance.workOrder;

import com.codex.ecam.dto.BaseDTO;

public class WorkOrderMeterReadingValueDTO extends BaseDTO {

	private Integer woMeterReadingId;
	private Integer woMeterReadingValueId;
	private Integer woMeterReadingValueIndex;
	private Integer woMeterReadingIndex;
	private Double woMeterReadingValue;
    private String woMeterReadingName;
    private String woMeterReadingValueAddedDateStr;
    private Long woMeterReadingValueAddedDate;	

	public Double getWoMeterReadingValue() {
		return woMeterReadingValue;
	}

	public void setWoMeterReadingValue(Double woMeterReadingValue) {
		this.woMeterReadingValue = woMeterReadingValue;
	}

	public Integer getWoMeterReadingValueId() {
		return woMeterReadingValueId;
	}

	public void setWoMeterReadingValueId(Integer woMeterReadingValueId) {
		this.woMeterReadingValueId = woMeterReadingValueId;
	}

	public Integer getWoMeterReadingValueIndex() {
		return woMeterReadingValueIndex;
	}

	public void setWoMeterReadingValueIndex(Integer woMeterReadingValueIndex) {
		this.woMeterReadingValueIndex = woMeterReadingValueIndex;
	}

	public Long getWoMeterReadingValueAddedDate() {
		return woMeterReadingValueAddedDate;
	}

	public void setWoMeterReadingValueAddedDate(Long woMeterReadingValueAddedDate) {
		this.woMeterReadingValueAddedDate = woMeterReadingValueAddedDate;
	}

	public Integer getWoMeterReadingId() {
		return woMeterReadingId;
	}

	public void setWoMeterReadingId(Integer woMeterReadingId) {
		this.woMeterReadingId = woMeterReadingId;
	}

	public Integer getWoMeterReadingIndex() {
		return woMeterReadingIndex;
	}

	public void setWoMeterReadingIndex(Integer woMeterReadingIndex) {
		this.woMeterReadingIndex = woMeterReadingIndex;
	}

	public String getWoMeterReadingName() {
		return woMeterReadingName;
	}

	public void setWoMeterReadingName(String woMeterReadingName) {
		this.woMeterReadingName = woMeterReadingName;
	}

	public String getWoMeterReadingValueAddedDateStr() {
		return woMeterReadingValueAddedDateStr;
	}

	public void setWoMeterReadingValueAddedDateStr(String woMeterReadingValueAddedDateStr) {
		this.woMeterReadingValueAddedDateStr = woMeterReadingValueAddedDateStr;
	}
	
	

}
