package com.codex.ecam.dto.report.data;


import java.math.BigDecimal;

import com.codex.ecam.dto.BaseReportDTO;

public class InventoryRepDTO extends BaseReportDTO {

	private String inventoryPartCode;
	private String inventoryPartDescription;
	private BigDecimal inventoryStockQty = BigDecimal.ZERO;
	private BigDecimal inventory = BigDecimal.ZERO;
	private BigDecimal averageCost = BigDecimal.ZERO;
	
	public String getInventoryPartCode() {
		return inventoryPartCode;
	}
	public void setInventoryPartCode(String inventoryPartCode) {
		this.inventoryPartCode = inventoryPartCode;
	}
	public String getInventoryPartDescription() {
		return inventoryPartDescription;
	}
	public void setInventoryPartDescription(String inventoryPartDescription) {
		this.inventoryPartDescription = inventoryPartDescription;
	}
	public BigDecimal getInventoryStockQty() {
		return inventoryStockQty;
	}
	public void setInventoryStockQty(BigDecimal inventoryStockQty) {
		this.inventoryStockQty = inventoryStockQty;
	}
	public BigDecimal getAverageCost() {
		return averageCost;
	}
	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}
	

}
