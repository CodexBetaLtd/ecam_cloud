package com.codex.ecam.dto.asset;

import com.codex.ecam.dto.BaseDTO;

public class AssetEventTypeAssetDTO extends BaseDTO {

    private Integer id;
    private Integer index;
    private Integer assetId;
    private Integer assetEventTypeId;
    private String assetEventTypeName;
    private Integer latestAssetEventId;
    private Integer latestAssetEventIndexId;
    private Long latestEventOccurDate;
    private String latestEventOccurDateStr;

    public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

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

    public Long getLatestEventOccurDate() {
        return latestEventOccurDate;
    }

    public void setLatestEventOccurDate(Long latestEventOccurDate) {
        this.latestEventOccurDate = latestEventOccurDate;
    }

    public Integer getLatestAssetEventId() {
        return latestAssetEventId;
    }

    public void setLatestAssetEventId(Integer latestAssetEventId) {
        this.latestAssetEventId = latestAssetEventId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getLatestAssetEventIndexId() {
        return latestAssetEventIndexId;
    }

    public void setLatestAssetEventIndexId(Integer latestAssetEventIndexId) {
        this.latestAssetEventIndexId = latestAssetEventIndexId;
    }

    public String getLatestEventOccurDateStr() {
        return latestEventOccurDateStr;
    }

    public void setLatestEventOccurDateStr(String latestEventOccurDateStr) {
        this.latestEventOccurDateStr = latestEventOccurDateStr;
    }

}
