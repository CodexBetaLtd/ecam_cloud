package com.codex.ecam.dto.asset;

import com.codex.ecam.dto.BaseDTO;

public class AssetMeterReadingValueConsumptionDTO extends BaseDTO {

    private Integer assetMeterReadingValueId;
    private String variable;
    private Double value;
    
	public Integer getAssetMeterReadingValueId() {
		return assetMeterReadingValueId;
	}
	public void setAssetMeterReadingValueId(Integer assetMeterReadingValueId) {
		this.assetMeterReadingValueId = assetMeterReadingValueId;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
    



}
