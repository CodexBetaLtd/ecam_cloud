package com.neolith.focus.dto.asset;

import com.neolith.focus.dto.BaseDTO;

public class AssetMeterReadingValueDTO extends BaseDTO {

    private Integer assetMeterReadingValueId;
    private Integer assetMeterReadingValueIndex;
    private Integer assetMeterReadingId;
    private Integer assetMeterReadingIndex;
    private String assetMeterReadingName;
    private Double assetMeterReadingValue;
    private Long assetMeterReadingValueAddedDate;
    private String assetMeterReadingValueAddedDateStr;
    private String unit;

    public Integer getAssetMeterReadingId() {
        return assetMeterReadingId;
    }

    public void setAssetMeterReadingId(Integer assetMeterReadingId) {
        this.assetMeterReadingId = assetMeterReadingId;
    }

    public Integer getAssetMeterReadingValueId() {
        return assetMeterReadingValueId;
    }

    public void setAssetMeterReadingValueId(Integer assetMeterReadingValueId) {
        this.assetMeterReadingValueId = assetMeterReadingValueId;
    }

    public String getAssetMeterReadingName() {
        return assetMeterReadingName;
    }

    public void setAssetMeterReadingName(String assetMeterReadingName) {
        this.assetMeterReadingName = assetMeterReadingName;
    }

    public Long getAssetMeterReadingValueAddedDate() {
        return assetMeterReadingValueAddedDate;
    }

    public void setAssetMeterReadingValueAddedDate(Long assetMeterReadingValueAddedDate) {
        this.assetMeterReadingValueAddedDate = assetMeterReadingValueAddedDate;
    }

    public Double getAssetMeterReadingValue() {
        return assetMeterReadingValue;
    }

    public void setAssetMeterReadingValue(Double assetMeterReadingValue) {
        this.assetMeterReadingValue = assetMeterReadingValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAssetMeterReadingValueAddedDateStr() {
        return assetMeterReadingValueAddedDateStr;
    }

    public void setAssetMeterReadingValueAddedDateStr(String assetMeterReadingValueAddedDateStr) {
        this.assetMeterReadingValueAddedDateStr = assetMeterReadingValueAddedDateStr;
    }

    public Integer getAssetMeterReadingIndex() {
        return assetMeterReadingIndex;
    }

    public void setAssetMeterReadingIndex(Integer assetMeterReadingIndex) {
        this.assetMeterReadingIndex = assetMeterReadingIndex;
    }

    public Integer getAssetMeterReadingValueIndex() {
        return assetMeterReadingValueIndex;
    }

    public void setAssetMeterReadingValueIndex(Integer assetMeterReadingValueIndex) {
        this.assetMeterReadingValueIndex = assetMeterReadingValueIndex;
    }

}
