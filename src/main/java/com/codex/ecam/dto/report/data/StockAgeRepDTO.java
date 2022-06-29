package com.codex.ecam.dto.report.data;


import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

public class StockAgeRepDTO extends BaseReportDTO {

	private Integer stockAgePartId;
	private Integer stockAgeWarehouseId;
	private Integer stockAgeDaysAge;

	private String stockAgeStockNo;
	private String stockAgewarehouseCode;
	private String stockAgePartCode;
	private String stockAgePartDescription;
	private String stockAgeTypeName;
	private String stockAgeItemTypeName;
	private String stockAgeLastIssueDate;
	private String stockAgeLastGRN;
	private String stockAgeLastAOD;

	private Date stockAgeDate;
	private Date stockAgeCreatedDate;
	private Date stockAgeLastModifyDate;
	private Date stockAgeLastAODDate;
	private Date stockAgeLastGRNDate;

	private BigDecimal stockAgePartUnitPrice = BigDecimal.ZERO;
	private BigDecimal stockAgeYears = BigDecimal.ZERO;
	private BigDecimal stockAgeMonths = BigDecimal.ZERO;
	private BigDecimal stockAgeDays = BigDecimal.ZERO;
	private BigDecimal stockAgeStockQty = BigDecimal.ZERO;
	private BigDecimal stockAgeRemainQty = BigDecimal.ZERO;
	private BigDecimal stockAgeTotalAmount = BigDecimal.ZERO;

	public Integer getStockAgePartId() {
		return stockAgePartId;
	}
	public void setStockAgePartId(Integer stockAgePartId) {
		this.stockAgePartId = stockAgePartId;
	}
	public Integer getStockAgeWarehouseId() {
		return stockAgeWarehouseId;
	}
	public void setStockAgeWarehouseId(Integer stockAgeWarehouseId) {
		this.stockAgeWarehouseId = stockAgeWarehouseId;
	}
	public Integer getStockAgeDaysAge() {
		return stockAgeDaysAge;
	}
	public void setStockAgeDaysAge(Integer stockAgeDaysAge) {
		this.stockAgeDaysAge = stockAgeDaysAge;
	}
	public String getStockAgeStockNo() {
		return stockAgeStockNo;
	}
	public void setStockAgeStockNo(String stockAgeStockNo) {
		this.stockAgeStockNo = stockAgeStockNo;
	}
	public String getStockAgewarehouseCode() {
		return stockAgewarehouseCode;
	}
	public void setStockAgewarehouseCode(String stockAgewarehouseCode) {
		this.stockAgewarehouseCode = stockAgewarehouseCode;
	}
	public String getStockAgePartCode() {
		return stockAgePartCode;
	}
	public void setStockAgePartCode(String stockAgePartCode) {
		this.stockAgePartCode = stockAgePartCode;
	}
	public String getStockAgePartDescription() {
		return stockAgePartDescription;
	}
	public void setStockAgePartDescription(String stockAgePartDescription) {
		this.stockAgePartDescription = stockAgePartDescription;
	}
	public String getStockAgeTypeName() {
		return stockAgeTypeName;
	}
	public void setStockAgeTypeName(String stockAgeTypeName) {
		this.stockAgeTypeName = stockAgeTypeName;
	}
	public String getStockAgeItemTypeName() {
		return stockAgeItemTypeName;
	}
	public void setStockAgeItemTypeName(String stockAgeItemTypeName) {
		this.stockAgeItemTypeName = stockAgeItemTypeName;
	}
	public String getStockAgeLastIssueDate() {
		return stockAgeLastIssueDate;
	}
	public void setStockAgeLastIssueDate(String stockAgeLastIssueDate) {
		this.stockAgeLastIssueDate = stockAgeLastIssueDate;
	}
	public String getStockAgeLastGRN() {
		return stockAgeLastGRN;
	}
	public void setStockAgeLastGRN(String stockAgeLastGRN) {
		this.stockAgeLastGRN = stockAgeLastGRN;
	}
	public String getStockAgeLastAOD() {
		return stockAgeLastAOD;
	}
	public void setStockAgeLastAOD(String stockAgeLastAOD) {
		this.stockAgeLastAOD = stockAgeLastAOD;
	}
	public Date getStockAgeDate() {
		return stockAgeDate;
	}
	public void setStockAgeDate(Date stockAgeDate) {
		this.stockAgeDate = stockAgeDate;
	}
	public Date getStockAgeCreatedDate() {
		return stockAgeCreatedDate;
	}
	public void setStockAgeCreatedDate(Date stockAgeCreatedDate) {
		this.stockAgeCreatedDate = stockAgeCreatedDate;
	}
	public Date getStockAgeLastModifyDate() {
		return stockAgeLastModifyDate;
	}
	public void setStockAgeLastModifyDate(Date stockAgeLastModifyDate) {
		this.stockAgeLastModifyDate = stockAgeLastModifyDate;
	}
	public Date getStockAgeLastAODDate() {
		return stockAgeLastAODDate;
	}
	public void setStockAgeLastAODDate(Date stockAgeLastAODDate) {
		this.stockAgeLastAODDate = stockAgeLastAODDate;
	}
	public Date getStockAgeLastGRNDate() {
		return stockAgeLastGRNDate;
	}
	public void setStockAgeLastGRNDate(Date stockAgeLastGRNDate) {
		this.stockAgeLastGRNDate = stockAgeLastGRNDate;
	}
	public BigDecimal getStockAgePartUnitPrice() {
		return stockAgePartUnitPrice;
	}
	public void setStockAgePartUnitPrice(BigDecimal stockAgePartUnitPrice) {
		this.stockAgePartUnitPrice = stockAgePartUnitPrice;
	}
	public BigDecimal getStockAgeRemainQty() {
		return stockAgeRemainQty;
	}
	public void setStockAgeRemainQty(BigDecimal stockAgeRemainQty) {
		this.stockAgeRemainQty = stockAgeRemainQty;
	}
	public BigDecimal getStockAgeYears() {
		return stockAgeYears;
	}
	public void setStockAgeYears(BigDecimal stockAgeYears) {
		this.stockAgeYears = stockAgeYears;
	}
	public BigDecimal getStockAgeMonths() {
		return stockAgeMonths;
	}
	public void setStockAgeMonths(BigDecimal stockAgeMonths) {
		this.stockAgeMonths = stockAgeMonths;
	}
	public BigDecimal getStockAgeDays() {
		return stockAgeDays;
	}
	public void setStockAgeDays(BigDecimal stockAgeDays) {
		this.stockAgeDays = stockAgeDays;
	}
	public BigDecimal getStockAgeStockQty() {
		return stockAgeStockQty;
	}
	public void setStockAgeStockQty(BigDecimal stockAgeStockQty) {
		this.stockAgeStockQty = stockAgeStockQty;
	}
	public BigDecimal getStockAgeTotalAmount() {
		return stockAgeTotalAmount;
	}
	public void setStockAgeTotalAmount(BigDecimal stockAgeTotalAmount) {
		this.stockAgeTotalAmount = stockAgeTotalAmount;
	}




}
