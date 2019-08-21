package com.codex.ecam.dto.admin;

import com.codex.ecam.dto.BaseDTO;

public class AssetModelDTO extends BaseDTO{

	private Integer modelId;
	private String modelName;
	private Integer brandId;
	private String brandName;
		
	public Integer getModelId() {
		return modelId;
	}
	
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
}
