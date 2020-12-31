package com.codex.ecam.dto.report.filter;



import java.util.Date;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.constants.inventory.StockAgeType;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class StockAgeFilterDTO extends BaseReportFilterDTO {


	private Integer itemId;
	private Integer stockId;
	private Integer warehouseId;

	private String itemCode;
	private String itemDecription;
	private String stockCode;
	private String warehouseName;

	private StockAgeType range;
	private PartType itemType;

	private Date stockDate;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDecription() {
		return itemDecription;
	}

	public void setItemDecription(String itemDecription) {
		this.itemDecription = itemDecription;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public StockAgeType getRange() {
		return range;
	}

	public void setRange(StockAgeType range) {
		this.range = range;
	}

	public PartType getItemType() {
		return itemType;
	}

	public void setItemType(PartType itemType) {
		this.itemType = itemType;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	
	
}
