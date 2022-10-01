package com.codex.ecam.dto.report.filter;



import java.util.Date;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.constants.inventory.AODType;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class AODReturnFilterDTO extends BaseReportFilterDTO {
	private static String templateName="AodReturnSummary";
	private static String templatePath="/inventory/aodReturn/summary/";
	private static String reportName="aod-return-list-report";

    private Integer businessId;
    private String businessName;
    private Integer requestedByUserId;
    private String requestedByUserName;

    private AODReturnStatus aodReturnStatus;
    private AODType aodType;
    private String aodNo;
    private Integer aodId;
    private String customerName;

    private Date fromDate;
    private Date toDate;
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getRequestedByUserId() {
		return requestedByUserId;
	}
	public void setRequestedByUserId(Integer requestedByUserId) {
		this.requestedByUserId = requestedByUserId;
	}
	public String getRequestedByUserName() {
		return requestedByUserName;
	}
	public void setRequestedByUserName(String requestedByUserName) {
		this.requestedByUserName = requestedByUserName;
	}

	public AODReturnStatus getAodReturnStatus() {
		return aodReturnStatus;
	}
	public void setAodReturnStatus(AODReturnStatus aodReturnStatus) {
		this.aodReturnStatus = aodReturnStatus;
	}
	public AODType getAodType() {
		return aodType;
	}
	public void setAodType(AODType aodType) {
		this.aodType = aodType;
	}
	public String getAodNo() {
		return aodNo;
	}
	public void setAodNo(String aodNo) {
		this.aodNo = aodNo;
	}

	public Integer getAodId() {
		return aodId;
	}
	public void setAodId(Integer aodId) {
		this.aodId = aodId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
		AODReturnFilterDTO.templateName = templateName;
	}

	public static String getTemplatePath() {
		return getPathToTemplate().concat(templatePath);
	}

	public static void setTemplatePath(String templatePath) {
		AODReturnFilterDTO.templatePath = templatePath;
	}

	public static String getReportName() {
		return reportName;
	}

	public static void setReportName(String reportName) {
		AODReturnFilterDTO.reportName = reportName;
	}

    
}
