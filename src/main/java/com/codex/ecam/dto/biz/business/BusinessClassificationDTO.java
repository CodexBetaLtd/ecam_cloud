package com.codex.ecam.dto.biz.business;

import com.codex.ecam.dto.BaseDTO;

public class BusinessClassificationDTO extends BaseDTO {

	private Integer id;
	private String name;

	private Integer businessId;

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

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

}
