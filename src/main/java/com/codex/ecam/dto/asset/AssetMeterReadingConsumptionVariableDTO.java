package com.codex.ecam.dto.asset;

import com.codex.ecam.dto.BaseDTO;

public class AssetMeterReadingConsumptionVariableDTO extends BaseDTO {

    private Integer assetMeterReadingId;
    private String variable;
    
	public Integer getAssetMeterReadingId() {
		return assetMeterReadingId;
	}
	public void setAssetMeterReadingId(Integer assetMeterReadingId) {
		this.assetMeterReadingId = assetMeterReadingId;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
    





}
