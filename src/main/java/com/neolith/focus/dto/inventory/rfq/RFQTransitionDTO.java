package com.neolith.focus.dto.inventory.rfq;

import com.neolith.focus.constants.inventory.RFQStatus;
import com.neolith.focus.dto.BaseDTO;


public class RFQTransitionDTO extends BaseDTO {
	
	private Integer id;
	private RFQStatus fromStatus;
	private RFQStatus toStatus;
	private String name;
	
	private String supplierName="aaa";
	private String email="wasantaha";
	private String comment="cddfg";
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public RFQStatus getFromStatus() {
		return fromStatus;
	}
	public void setFromStatus(RFQStatus fromStatus) {
		this.fromStatus = fromStatus;
	}
	public RFQStatus getToStatus() {
		return toStatus;
	}
	public void setToStatus(RFQStatus toStatus) {
		this.toStatus = toStatus;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
