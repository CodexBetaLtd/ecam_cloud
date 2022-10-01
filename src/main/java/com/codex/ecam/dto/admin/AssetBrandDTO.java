package com.codex.ecam.dto.admin;

import com.codex.ecam.dto.BaseDTO;

public class AssetBrandDTO extends BaseDTO{

	private Integer brandId;
	private Integer brandBusinessId;

	private String brandName;
	private String brandBusinessName;

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

	public Integer getBrandBusinessId() {
		return brandBusinessId;
	}

	public String getBrandBusinessName() {
		return brandBusinessName;
	}

	public void setBrandBusinessId(Integer brandBusinessId) {
		this.brandBusinessId = brandBusinessId;
	}

	public void setBrandBusinessName(String brandBusinessName) {
		this.brandBusinessName = brandBusinessName;
	}

}
