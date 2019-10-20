package com.codex.ecam.dto.asset;

import com.codex.ecam.dto.BaseDTO;

public class AssetMeterReadingConsumptionVariableDTO extends BaseDTO {

	private Integer id;
	private Integer assetMeterReadingId;
	private Integer meteReadingUnitId;
    private String meteReadingUnitName;
    
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
	public Integer getMeteReadingUnitId() {
		return meteReadingUnitId;
	}
	public void setMeteReadingUnitId(Integer meteReadingUnitId) {
		this.meteReadingUnitId = meteReadingUnitId;
	}
	public String getMeteReadingUnitName() {
		return meteReadingUnitName;
	}
	public void setMeteReadingUnitName(String meteReadingUnitName) {
		this.meteReadingUnitName = meteReadingUnitName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
