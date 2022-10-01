package com.codex.ecam.dto.asset;

import com.codex.ecam.dto.BaseDTO;

public class AssetEventDTO extends BaseDTO {

    private Integer assetEventId;
    private Integer assetEventIndex;
    private Integer assetEventTypeAssetId;
    private Integer assetEventTypeId;
    private String assetEventTypeName;
    private String assetEventDescription;
    private Long assetEventOccuredDate;
    private String assetEventOccuredDateStr;

	public Integer getAssetEventTypeId() {
		return assetEventTypeId;
	}

	public void setAssetEventTypeId(Integer assetEventTypeId) {
		this.assetEventTypeId = assetEventTypeId;
	}

    public String getAssetEventTypeName() {
        return assetEventTypeName;
    }

    public void setAssetEventTypeName(String assetEventTypeName) {
        this.assetEventTypeName = assetEventTypeName;
    }

    public Integer getAssetEventTypeAssetId() {
        return assetEventTypeAssetId;
    }

    public void setAssetEventTypeAssetId(Integer assetEventTypeAssetId) {
        this.assetEventTypeAssetId = assetEventTypeAssetId;
    }

    public Integer getAssetEventId() {
        return assetEventId;
    }

    public void setAssetEventId(Integer assetEventId) {
        this.assetEventId = assetEventId;
    }

    public Integer getAssetEventIndex() {
        return assetEventIndex;
    }

    public void setAssetEventIndex(Integer assetEventIndex) {
        this.assetEventIndex = assetEventIndex;
    }

    public String getAssetEventDescription() {
        return assetEventDescription;
    }

    public void setAssetEventDescription(String assetEventDescription) {
        this.assetEventDescription = assetEventDescription;
    }

    public Long getAssetEventOccuredDate() {
        return assetEventOccuredDate;
    }

    public void setAssetEventOccuredDate(Long assetEventOccuredDate) {    	
        this.assetEventOccuredDate = assetEventOccuredDate;
    }

    public String getAssetEventOccuredDateStr() {
        return assetEventOccuredDateStr;
    }

    public void setAssetEventOccuredDateStr(String assetEventOccuredDateStr) {
        this.assetEventOccuredDateStr = assetEventOccuredDateStr;
    }

}
