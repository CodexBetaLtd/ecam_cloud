package com.codex.ecam.dto.report.data.purchaseorder;

public class PurchaseorderRepItemDTO  {

	private String partName;
	private String partCode;
	private String description;
	private Double itemQuantity;
	private Double itemCost;
	private Double itemAmount;
	private Double taxValue;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Double getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}
	public Double getTaxValue() {
		return taxValue;
	}
	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}


  



}
