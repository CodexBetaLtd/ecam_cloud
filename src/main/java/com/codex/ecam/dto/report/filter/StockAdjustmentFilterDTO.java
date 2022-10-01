package com.codex.ecam.dto.report.filter;



import java.util.Date;

import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class StockAdjustmentFilterDTO extends BaseReportFilterDTO {

	private static String templateName="StockAdjustmentSummary";
	private static String templatePath="/inventory/StockAdjustment/summary/";
	private static String reportName="stock-adjusment-list-report";
	
    private Integer itemId;
    private String itemName;
   private StockAdjustmentStatus adjustmentStatus;
    private Integer stockId;
    private String stockCode;

    private Date fromDate;
    private Date toDate;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public StockAdjustmentStatus getAdjustmentStatus() {
		return adjustmentStatus;
	}
	public void setAdjustmentStatus(StockAdjustmentStatus adjustmentStatus) {
		this.adjustmentStatus = adjustmentStatus;
	}
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public static String getTemplateName() {
		return templateName.concat(getTemplateType());
	}
	public static void setTemplateName(String templateName) {
		StockAdjustmentFilterDTO.templateName = templateName;
	}
	public static String getTemplatePath() {
		return getPathToTemplate().concat(templatePath);
	}
	public static void setTemplatePath(String templatePath) {
		StockAdjustmentFilterDTO.templatePath = templatePath;
	}
	public static String getReportName() {
		return reportName;
	}
	public static void setReportName(String reportName) {
		StockAdjustmentFilterDTO.reportName = reportName;
	}



    
}
