package com.codex.ecam.dto.report.filter;



import java.util.Date;

import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderType;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class ReceiptOrderFilterDTO extends BaseReportFilterDTO {

	private static String templateName="ReceiptOrderSummary";
	private static String templatePath="/inventory/receiptOrder/summary/";
	private static String reportName="ReceiptOrder-list-report";
	
    private Integer businessId;
    private String businessName;
    private Integer siteId;
    private String siteName;

    private ReceiptOrderStatus grnStatus;
    private ReceiptOrderType grnType;
    private String grnNo;
    private Integer supplierId;
    private String supplierName;

    private Date dateOrdered;
    private Date dateOrderedFrom;
    private Date dateOrderedTo;

    private Date dateReceived;
    private Date dateReceivedFrom;
    private Date dateReceivedTo;

    private String invoiceNo;
    private String orderNo;

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

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public ReceiptOrderStatus getGrnStatus() {
        return grnStatus;
    }

    public void setGrnStatus(ReceiptOrderStatus grnStatus) {
        this.grnStatus = grnStatus;
    }

    public String getGrnNo() {
        return grnNo;
    }

    public void setGrnNo(String grnNo) {
        this.grnNo = grnNo;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getDateOrderedFrom() {
        return dateOrderedFrom;
    }

    public void setDateOrderedFrom(Date dateOrderedFrom) {
        this.dateOrderedFrom = dateOrderedFrom;
    }

    public Date getDateOrderedTo() {
        return dateOrderedTo;
    }

    public void setDateOrderedTo(Date dateOrderedTo) {
        this.dateOrderedTo = dateOrderedTo;
    }

    public Date getDateReceivedFrom() {
        return dateReceivedFrom;
    }

    public void setDateReceivedFrom(Date dateReceivedFrom) {
        this.dateReceivedFrom = dateReceivedFrom;
    }

    public Date getDateReceivedTo() {
        return dateReceivedTo;
    }

    public void setDateReceivedTo(Date dateReceivedTo) {
        this.dateReceivedTo = dateReceivedTo;
    }

	public ReceiptOrderType getGrnType() {
		return grnType;
	}

	public void setGrnType(ReceiptOrderType grnType) {
		this.grnType = grnType;
	}

	public static String getTemplateName() {
		return templateName.concat(getTemplateType());
	}

	public static void setTemplateName(String templateName) {
		ReceiptOrderFilterDTO.templateName = templateName;
	}

	public static String getTemplatePath() {
		return getPathToTemplate().concat(templatePath);
	}

	public static void setTemplatePath(String templatePath) {
		ReceiptOrderFilterDTO.templatePath = templatePath;
	}

	public static String getReportName() {
		return reportName;
	}

	public static void setReportName(String reportName) {
		ReceiptOrderFilterDTO.reportName = reportName;
	}
    
    
}
