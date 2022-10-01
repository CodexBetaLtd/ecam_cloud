package com.codex.ecam.dto.report.data.mrn;


import java.util.Date;


public class MRNRepItemDTO  {

	private String siteName;
	private String partName;
	private String partCode;
	private String stockNo;
	private String description;
	private String batchNo;
	private String jobNo;
	private Double itemQuantity;
	private Double itemCost;
	private Double itemWeightedCost;
	private Double itemAmount;

	private Double itemReturnQuantity;

	private Date aodDate;
	private String aodNo;
	private String aodJobNo;
	private String aodRequestedUserName;
	private String aodCustomerName;
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public Double getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(Double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public Double getItemCost() {
		return itemCost;
	}
	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}
	public Double getItemWeightedCost() {
		return itemWeightedCost;
	}
	public void setItemWeightedCost(Double itemWeightedCost) {
		this.itemWeightedCost = itemWeightedCost;
	}
	public Double getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}
	public Double getItemReturnQuantity() {
		return itemReturnQuantity;
	}
	public void setItemReturnQuantity(Double itemReturnQuantity) {
		this.itemReturnQuantity = itemReturnQuantity;
	}
	public Date getAodDate() {
		return aodDate;
	}
	public void setAodDate(Date aodDate) {
		this.aodDate = aodDate;
	}
	public String getAodNo() {
		return aodNo;
	}
	public void setAodNo(String aodNo) {
		this.aodNo = aodNo;
	}
	public String getAodJobNo() {
		return aodJobNo;
	}
	public void setAodJobNo(String aodJobNo) {
		this.aodJobNo = aodJobNo;
	}
	public String getAodRequestedUserName() {
		return aodRequestedUserName;
	}
	public void setAodRequestedUserName(String aodRequestedUserName) {
		this.aodRequestedUserName = aodRequestedUserName;
	}
	public String getAodCustomerName() {
		return aodCustomerName;
	}
	public void setAodCustomerName(String aodCustomerName) {
		this.aodCustomerName = aodCustomerName;
	}
    
  



}
