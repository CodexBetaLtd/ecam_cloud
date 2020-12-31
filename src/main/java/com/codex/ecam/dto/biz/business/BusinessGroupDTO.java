package com.codex.ecam.dto.biz.business;

import com.codex.ecam.dto.BaseDTO;

public class BusinessGroupDTO extends BaseDTO {

	private Integer id;
	private String name;
	private Integer relationshipType;
	private Boolean isDefaultManufacturer;
	private Boolean isDefaultSupplier;

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

	public Integer getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(Integer relationshipType) {
		this.relationshipType = relationshipType;
	}

	public Boolean getIsDefaultManufacturer() {
		return isDefaultManufacturer;
	}

	public void setIsDefaultManufacturer(Boolean isDefaultManufacturer) {
		this.isDefaultManufacturer = isDefaultManufacturer;
	}

	public Boolean getIsDefaultSupplier() {
		return isDefaultSupplier;
	}

	public void setIsDefaultSupplier(Boolean isDefaultSupplier) {
		this.isDefaultSupplier = isDefaultSupplier;
	}

}
