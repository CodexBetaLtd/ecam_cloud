package com.neolith.focus.dto.asset;

import com.neolith.focus.dto.BaseDTO;

public class AssetMeterReadingDTO extends BaseDTO {

	private Integer meterReadingId;
	private Integer meterReadingIndex;
	private Integer meterReadingAssetId;
	private Integer meterReadingUnitId;
	private Integer meterReadingCurrentValueIndex;
	private Integer meterReadingCurrentValueId;

	private String meterReadingName;
	private String meterReadingViewName;
	private String meterReadingDescription;
	private String meterReadingUnitName;

	private Double meterReadingCurrentValue;
	private Double meterReadingAvgValue = 0.00;

	public String getMeterReadingName() {
		return meterReadingName;
	}

	public void setMeterReadingName(String meterReadingName) {
		this.meterReadingName = meterReadingName;
	}

	public Integer getMeterReadingUnitId() {
		return meterReadingUnitId;
	}

	public void setMeterReadingUnitId(Integer meterReadingUnitId) {
		this.meterReadingUnitId = meterReadingUnitId;
	}

	public String getMeterReadingViewName() {
		return meterReadingViewName;
	}

	public void setMeterReadingViewName(String meterReadingViewName) {
		this.meterReadingViewName = meterReadingViewName;
	}

	public String getMeterReadingUnitName() {
		return meterReadingUnitName;
	}

	public void setMeterReadingUnitName(String meterReadingUnitName) {
		this.meterReadingUnitName = meterReadingUnitName;
	}

	public Integer getMeterReadingId() {
		return meterReadingId;
	}

	public void setMeterReadingId(Integer meterReadingId) {
		this.meterReadingId = meterReadingId;
	}

	public Integer getMeterReadingIndex() {
		return meterReadingIndex;
	}

	public void setMeterReadingIndex(Integer meterReadingIndex) {
		this.meterReadingIndex = meterReadingIndex;
	}

	public Integer getMeterReadingAssetId() {
		return meterReadingAssetId;
	}

	public void setMeterReadingAssetId(Integer meterReadingAssetId) {
		this.meterReadingAssetId = meterReadingAssetId;
	}

	public Integer getMeterReadingCurrentValueIndex() {
		return meterReadingCurrentValueIndex;
	}

	public void setMeterReadingCurrentValueIndex(Integer meterReadingCurrentValueIndex) {
		this.meterReadingCurrentValueIndex = meterReadingCurrentValueIndex;
	}

	public Integer getMeterReadingCurrentValueId() {
		return meterReadingCurrentValueId;
	}

	public void setMeterReadingCurrentValueId(Integer meterReadingCurrentValueId) {
		this.meterReadingCurrentValueId = meterReadingCurrentValueId;
	}

	public String getMeterReadingDescription() {
		return meterReadingDescription;
	}

	public void setMeterReadingDescription(String meterReadingDescription) {
		this.meterReadingDescription = meterReadingDescription;
	}

	public Double getMeterReadingCurrentValue() {
		return meterReadingCurrentValue;
	}

	public void setMeterReadingCurrentValue(Double meterReadingCurrentValue) {
		this.meterReadingCurrentValue = meterReadingCurrentValue;
	}

	public Double getMeterReadingAvgValue() {
		return meterReadingAvgValue;
	}

	public void setMeterReadingAvgValue(Double meterReadingAvgValue) {
		this.meterReadingAvgValue = meterReadingAvgValue;
	}

}
