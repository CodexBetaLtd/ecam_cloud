package com.codex.ecam.dto.asset;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class SparePartDTO extends BaseDTO {
	
	private Integer id;	
	private Integer partId;	
	private String partCode;
	private String description;
	private BigDecimal quantity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}	


}
