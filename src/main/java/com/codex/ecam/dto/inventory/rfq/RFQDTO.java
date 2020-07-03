package com.codex.ecam.dto.inventory.rfq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.biz.supplier.SupplierDTO;

public class RFQDTO extends BaseDTO {

    private Integer id;
    private Integer businessId;
    private Integer siteId;
    private String code;

    private Date expectedDeliveryDate;
    private Date requiredResponseDate;
    private Date sentDate;
    private String statusName;
    
    private String messageContent;
    private String messageSubject;
    private String quoteReferenceNumber;

    private Integer supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierCity;
    private String supplierProvince;
    private String supplierPostalCode;
    private Integer supplierCountry;

    private Integer billToId;
    private String billToAddress;
    private String billingCity;
    private String billingProvince;
    private String billingPostalCode;
    private Integer billToCountry;
      
    private Integer shipToId;
    private String shipToAddress;
    private String shippingCity;
    private String shippingProvince;
    private String shippingPostalCode;
    private Integer shipToCountry;
    
    private RFQStatus rfqStatus = RFQStatus.DRAFT;
    
    private List<RFQItemDTO> items = new ArrayList<>();
    private List<RFQFileDTO> rfqFileDTOs = new ArrayList<>();
    private List<RFQSupplierDTO> rfqSupplireDTOs = new ArrayList<>();
    private List<RFQNotificationDTO> notificationDTOs = new ArrayList<>();
	private List<RFQChangeLogDTO> rfqStatusChangeDTOs = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public Date getRequiredResponseDate() {
		return requiredResponseDate;
	}
	public void setRequiredResponseDate(Date requiredResponseDate) {
		this.requiredResponseDate = requiredResponseDate;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageSubject() {
		return messageSubject;
	}
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}
	public String getQuoteReferenceNumber() {
		return quoteReferenceNumber;
	}
	public void setQuoteReferenceNumber(String quoteReferenceNumber) {
		this.quoteReferenceNumber = quoteReferenceNumber;
	}
	public RFQStatus getRfqStatus() {
		return rfqStatus;
	}
	public void setRfqStatus(RFQStatus rfqStatus) {
		this.rfqStatus = rfqStatus;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierAddress() {
		return supplierAddress;
	}
	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	public String getSupplierCity() {
		return supplierCity;
	}
	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
	public String getSupplierProvince() {
		return supplierProvince;
	}
	public void setSupplierProvince(String supplierProvince) {
		this.supplierProvince = supplierProvince;
	}
	public String getSupplierPostalCode() {
		return supplierPostalCode;
	}
	public void setSupplierPostalCode(String supplierPostalCode) {
		this.supplierPostalCode = supplierPostalCode;
	}
	public Integer getSupplierCountry() {
		return supplierCountry;
	}
	public void setSupplierCountry(Integer supplierCountry) {
		this.supplierCountry = supplierCountry;
	}
	public Integer getBillToId() {
		return billToId;
	}
	public void setBillToId(Integer billToId) {
		this.billToId = billToId;
	}
	public String getBillToAddress() {
		return billToAddress;
	}
	public void setBillToAddress(String billToAddress) {
		this.billToAddress = billToAddress;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingProvince() {
		return billingProvince;
	}
	public void setBillingProvince(String billingProvince) {
		this.billingProvince = billingProvince;
	}
	public String getBillingPostalCode() {
		return billingPostalCode;
	}
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}
	public Integer getBillToCountry() {
		return billToCountry;
	}
	public void setBillToCountry(Integer billToCountry) {
		this.billToCountry = billToCountry;
	}
	public Integer getShipToId() {
		return shipToId;
	}
	public void setShipToId(Integer shipToId) {
		this.shipToId = shipToId;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	public String getShippingProvince() {
		return shippingProvince;
	}
	public void setShippingProvince(String shippingProvince) {
		this.shippingProvince = shippingProvince;
	}
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}
	public Integer getShipToCountry() {
		return shipToCountry;
	}
	public void setShipToCountry(Integer shipToCountry) {
		this.shipToCountry = shipToCountry;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public List<RFQItemDTO> getItems() {
		return items;
	}
	public void setItems(List<RFQItemDTO> items) {
		this.items = items;
	}
	public List<RFQFileDTO> getRfqFileDTOs() {
		return rfqFileDTOs;
	}
	public void setRfqFileDTOs(List<RFQFileDTO> rfqFileDTOs) {
		this.rfqFileDTOs = rfqFileDTOs;
	}
	public List<RFQNotificationDTO> getNotificationDTOs() {
		return notificationDTOs;
	}
	public void setNotificationDTOs(List<RFQNotificationDTO> notificationDTOs) {
		this.notificationDTOs = notificationDTOs;
	}
	public List<RFQChangeLogDTO> getRfqStatusChangeDTOs() {
		return rfqStatusChangeDTOs;
	}
	public void setRfqStatusChangeDTOs(List<RFQChangeLogDTO> rfqStatusChangeDTOs) {
		this.rfqStatusChangeDTOs = rfqStatusChangeDTOs;
	}
	public List<RFQSupplierDTO> getRfqSupplireDTOs() {
		return rfqSupplireDTOs;
	}
	public void setRfqSupplireDTOs(List<RFQSupplierDTO> rfqSupplireDTOs) {
		this.rfqSupplireDTOs = rfqSupplireDTOs;
	}


	
   
}
