package com.codex.ecam.dto.report.data;


import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

public class StockAdjustmentRepDTO extends BaseReportDTO {

    private String adjustmentCode;
    private String partName;
    private String stockNo;
    private String description;
    private Date date;
    private BigDecimal lastQuantity;
    private BigDecimal newQuantity;
    private String statusName;
    
	public String getAdjustmentCode() {
		return adjustmentCode;
	}
	public void setAdjustmentCode(String adjustmentCode) {
		this.adjustmentCode = adjustmentCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getLastQuantity() {
		return lastQuantity;
	}
	public void setLastQuantity(BigDecimal lastQuantity) {
		this.lastQuantity = lastQuantity;
	}
	public BigDecimal getNewQuantity() {
		return newQuantity;
	}
	public void setNewQuantity(BigDecimal newQuantity) {
		this.newQuantity = newQuantity;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
    




}
