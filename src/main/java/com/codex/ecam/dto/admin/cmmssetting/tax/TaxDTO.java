package com.codex.ecam.dto.admin.cmmssetting.tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.TAXType;
import com.codex.ecam.dto.BaseDTO;

public class TaxDTO extends BaseDTO {

	private Integer id;
	private String name;
	private String description;
	private Integer priorty;
	private Integer businessId;
	private String businessName;
	private TAXType taxType=TAXType.PERCENTAGE;
	private List<TaxValueDTO> taxValueDTOs = new ArrayList<>();
	
    private Integer currentValueid;
    private Date currentValueStartDate;
    private Date currentValueEndDate;
    private BigDecimal currentValue;

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

	public Integer getPriorty() {
		return priorty;
	}

	public void setPriorty(Integer priorty) {
		this.priorty = priorty;
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

	public List<TaxValueDTO> getTaxValueDTOs() {
		return taxValueDTOs;
	}

	public void setTaxValueDTOs(List<TaxValueDTO> taxValueDTOs) {
		this.taxValueDTOs = taxValueDTOs;
	}

	public Integer getCurrentValueid() {
		return currentValueid;
	}

	public void setCurrentValueid(Integer currentValueid) {
		this.currentValueid = currentValueid;
	}

	public Date getCurrentValueStartDate() {
		return currentValueStartDate;
	}

	public void setCurrentValueStartDate(Date currentValueStartDate) {
		this.currentValueStartDate = currentValueStartDate;
	}

	public Date getCurrentValueEndDate() {
		return currentValueEndDate;
	}

	public void setCurrentValueEndDate(Date currentValueEndDate) {
		this.currentValueEndDate = currentValueEndDate;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}

	public TAXType getTaxType() {
		return taxType;
	}

	public void setTaxType(TAXType taxType) {
		this.taxType = taxType;
	}
	
	

}
