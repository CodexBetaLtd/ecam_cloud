package com.codex.ecam.dto.report.data.purchaseorder;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;

public class PurchaseorderRepDTO extends BaseReportDTO {

    private String poNo;
    private String suppierName;
    private String supplierNo;
    private Date expectedDeliveryDate;
    private Double totalItemCost;
    private String poStatus;
    private String woNo;
    private String requestedBy;
    private String businessName;
    private String businessAddress;
    private List<PurchaseorderRepItemDTO> purchaseorderRepItemDTOs=new ArrayList<>();    
    private List<PurchaseorderRepTaxDTO> purchaseorderRepTaxDTOs=new ArrayList<>();    
    

	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getSuppierName() {
		return suppierName;
	}
	public void setSuppierName(String suppierName) {
		this.suppierName = suppierName;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Double getTotalItemCost() {
		return totalItemCost;
	}
	public void setTotalItemCost(Double totalItemCost) {
		this.totalItemCost = totalItemCost;
	}
	public String getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
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
	public List<PurchaseorderRepItemDTO> getPurchaseorderRepItemDTOs() {
		return purchaseorderRepItemDTOs;
	}
	public void setPurchaseorderRepItemDTOs(List<PurchaseorderRepItemDTO> purchaseorderRepItemDTOs) {
		this.purchaseorderRepItemDTOs = purchaseorderRepItemDTOs;
	}
	public List<PurchaseorderRepTaxDTO> getPurchaseorderRepTaxDTOs() {
		return purchaseorderRepTaxDTOs;
	}
	public void setPurchaseorderRepTaxDTOs(List<PurchaseorderRepTaxDTO> purchaseorderRepTaxDTOs) {
		this.purchaseorderRepTaxDTOs = purchaseorderRepTaxDTOs;
	}





}
