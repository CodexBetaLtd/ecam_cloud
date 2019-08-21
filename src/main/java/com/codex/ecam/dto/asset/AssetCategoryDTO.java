package com.codex.ecam.dto.asset;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.BaseDTO;

public class AssetCategoryDTO extends BaseDTO {
	
	private Integer id;
	private String name;
	private String description;
	private String overideRule;
	private AssetCategoryType type;
	private Integer parentId;
	private String parentName;

	private Integer sysCode;
	
	private Integer businessId;
	private String businessName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOverideRule() {
		return overideRule;
	}
	public void setOverideRule(String overideRule) {
		this.overideRule = overideRule;
	}
	public Integer getSysCode() {
		return sysCode;
	}
	public void setSysCode(Integer sysCode) {
		this.sysCode = sysCode;
	}
	public AssetCategoryType getType() {
		return type;
	}
	public void setType(AssetCategoryType type) {
		this.type = type;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;

	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	
	

}
