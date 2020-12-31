package com.codex.ecam.dto.report.data.receiptOrder;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;

public class ReceiptOrderRepDTO extends BaseReportDTO {

    private String code;
    private String orderNo;
    private Date dateOrdered;
    private Date dateReceived;
    private String invoiceNo;
    private Double currencyRate;
    private String remark;
    private String createdUser;

    private Boolean taxInclude;
    private Boolean taxBreakdown;
    private String supplierName;
    private String supplierAddressLine1;
    private String supplierAddressLine2;
    private String supplierAddressLine3;
    private String supplierCountry;
 
    private String status;

    private Double taxVATAmount;
    private Double taxNBTAmount;
    
    private String businessName;
    private String businessAddress;

    private List<ReceiptOrderItemRepDTO> itemRepDTOs=new ArrayList<>();    


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
    public String getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
    public Double getCurrencyRate() {
        return currencyRate;
    }
    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplierAddressLine1() {
        return supplierAddressLine1;
    }
    public void setSupplierAddressLine1(String supplierAddressLine1) {
        this.supplierAddressLine1 = supplierAddressLine1;
    }
    public String getSupplierAddressLine2() {
        return supplierAddressLine2;
    }
    public void setSupplierAddressLine2(String supplierAddressLine2) {
        this.supplierAddressLine2 = supplierAddressLine2;
    }
    public String getSupplierAddressLine3() {
        return supplierAddressLine3;
    }
    public void setSupplierAddressLine3(String supplierAddressLine3) {
        this.supplierAddressLine3 = supplierAddressLine3;
    }
    public String getSupplierCountry() {
        return supplierCountry;
    }
    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    public Boolean getTaxInclude() {
        return taxInclude;
    }

    public void setTaxInclude(Boolean taxInclude) {
        this.taxInclude = taxInclude;
    }

    public Boolean getTaxBreakdown() {
        return taxBreakdown;
    }

    public void setTaxBreakdown(Boolean taxBreakdown) {
        this.taxBreakdown = taxBreakdown;
    }

    public Double getTaxVATAmount() {
        return taxVATAmount;
    }

    public void setTaxVATAmount(Double taxVATAmount) {
        this.taxVATAmount = taxVATAmount;
    }

    public Double getTaxNBTAmount() {
        return taxNBTAmount;
    }

    public void setTaxNBTAmount(Double taxNBTAmount) {
        this.taxNBTAmount = taxNBTAmount;
    }
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public List<ReceiptOrderItemRepDTO> getItemRepDTOs() {
		return itemRepDTOs;
	}
	public void setItemRepDTOs(List<ReceiptOrderItemRepDTO> itemRepDTOs) {
		this.itemRepDTOs = itemRepDTOs;
	}
    
    
}
