package com.neolith.focus.dto.admin;

import com.neolith.focus.dto.BaseDTO;

public class UserJobTitleDTO extends BaseDTO {

	private Integer id;
	private Integer businessId;

	private String jobTitle;
	private String description;
	private String businessName; 

	public Integer getId() {
		return id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
