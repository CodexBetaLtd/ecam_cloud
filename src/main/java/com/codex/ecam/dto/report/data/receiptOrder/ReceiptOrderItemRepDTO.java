package com.codex.ecam.dto.report.data.receiptOrder;

import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

public class ReceiptOrderItemRepDTO extends BaseReportDTO {

	private String grnNo;
	private Date date;
	private String invoiceNo;
	private String remarks;
	private String description;
	private String itemCode;
	private Integer itemId;
	private Double quantity;
	private Double unitCost;
	private Double weightedCost;
	private Double amount;
	private String status;

	public String getGrnNo() {
		return grnNo;
	}

	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getWeightedCost() {
		return weightedCost;
	}

	public void setWeightedCost(Double weightedCost) {
		this.weightedCost = weightedCost;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
