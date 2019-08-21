package com.neolith.focus.dto.maintenance.workOrder;

import com.neolith.focus.dto.BaseDTO;

public class WorkOrderMeterReadingDTO extends BaseDTO {

    private Integer id;
    private Integer workOrderId;
    private String meterReadingDescription;
    private Integer meterReadingId;
    private String meterReadingName;
    private Integer meterReadingAssetId;
    private String meterReadingAssetName;
    private Double meterReadingCurrentValue; 
    private Integer meterReadingCurrentValueId;
    private Integer meterReadingCurrentValueIndex;
    private Long meterReadingValueAddedDate;
    private String meterReadingValueAddedDateStr;
    
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

    public String getMeterReadingDescription() {
        return meterReadingDescription;
    }

    public void setMeterReadingDescription(String meterReadingDescription) {
        this.meterReadingDescription = meterReadingDescription;
    }

    public Integer getMeterReadingId() {
        return meterReadingId;
    }

    public void setMeterReadingId(Integer meterReadingId) {
        this.meterReadingId = meterReadingId;
    }

    public String getMeterReadingName() {
        return meterReadingName;
    }

    public void setMeterReadingName(String meterReadingName) {
        this.meterReadingName = meterReadingName;
    }

    public Integer getMeterReadingAssetId() {
        return meterReadingAssetId;
    }

    public void setMeterReadingAssetId(Integer meterReadingAssetId) {
        this.meterReadingAssetId = meterReadingAssetId;
    }

    public String getMeterReadingAssetName() {
        return meterReadingAssetName;
    }

    public void setMeterReadingAssetName(String meterReadingAssetName) {
        this.meterReadingAssetName = meterReadingAssetName;
    }


	public Integer getMeterReadingCurrentValueId() {
		return meterReadingCurrentValueId;
	}

	public void setMeterReadingCurrentValueId(Integer meterReadingCurrentValueId) {
		this.meterReadingCurrentValueId = meterReadingCurrentValueId;
	}

	public Double getMeterReadingCurrentValue() {
		return meterReadingCurrentValue;
	}

	public void setMeterReadingCurrentValue(Double meterReadingCurrentValue) {
		this.meterReadingCurrentValue = meterReadingCurrentValue;
	}

	public Long getMeterReadingValueAddedDate() {
		return meterReadingValueAddedDate;
	}

	public void setMeterReadingValueAddedDate(Long meterReadingValueAddedDate) {
		this.meterReadingValueAddedDate = meterReadingValueAddedDate;
	}

	public String getMeterReadingValueAddedDateStr() {
		return meterReadingValueAddedDateStr;
	}

	public void setMeterReadingValueAddedDateStr(String meterReadingValueAddedDateStr) {
		this.meterReadingValueAddedDateStr = meterReadingValueAddedDateStr;
	}

	public Integer getMeterReadingCurrentValueIndex() {
		return meterReadingCurrentValueIndex;
	}

	public void setMeterReadingCurrentValueIndex(Integer meterReadingCurrentValueIndex) {
		this.meterReadingCurrentValueIndex = meterReadingCurrentValueIndex;
	}

}
