package com.codex.ecam.dto.inventory.purchaseOrder;

import java.util.Date;

import com.codex.ecam.dto.BaseDTO;

public class PurchaseOrderChangeLogDTO extends BaseDTO {

    private Integer id;
    private Date statusChangeDate;
    private String changeUserName;
    private String description;
    private String statusName;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStatusChangeDate() {
		return statusChangeDate;
	}
	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	public String getChangeUserName() {
		return changeUserName;
	}
	public void setChangeUserName(String changeUserName) {
		this.changeUserName = changeUserName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
    
	    
   
}
