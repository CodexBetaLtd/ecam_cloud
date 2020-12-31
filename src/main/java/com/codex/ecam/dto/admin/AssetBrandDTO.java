package com.codex.ecam.dto.admin;

import com.codex.ecam.dto.BaseDTO;

public class AssetBrandDTO extends BaseDTO{
	
	private Integer brandId;
	private String brandName;
	
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
