package com.codex.ecam.dto.biz.business;

import com.codex.ecam.dto.BaseDTO;

public class BussinessTypeDTO extends BaseDTO {

	private Integer id;
	private Boolean defaultParentId;
	private Boolean definable;
	private String allParent;
	private String businessTypeDefinitionName;
	private String businessTypeDefinitionShort;

	private Integer businessId;
	private String businessName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getDefaultParentId() {
		return defaultParentId;
	}

	public void setDefaultParentId(Boolean defaultParentId) {
		this.defaultParentId = defaultParentId;
	}

	public Boolean getDefinable() {
		return definable;
	}

	public void setDefinable(Boolean definable) {
		this.definable = definable;
	}

	public String getAllParent() {
		return allParent;
	}

	public void setAllParent(String allParent) {
		this.allParent = allParent;
	}

	public String getBusinessTypeDefinitionName() {
		return businessTypeDefinitionName;
	}

	public void setBusinessTypeDefinitionName(String businessTypeDefinitionName) {
		this.businessTypeDefinitionName = businessTypeDefinitionName;
	}

	public String getBusinessTypeDefinitionShort() {
		return businessTypeDefinitionShort;
	}

	public void setBusinessTypeDefinitionShort(String businessTypeDefinitionShort) {
		this.businessTypeDefinitionShort = businessTypeDefinitionShort;
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
