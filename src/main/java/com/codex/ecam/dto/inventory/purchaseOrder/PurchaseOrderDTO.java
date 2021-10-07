package com.codex.ecam.dto.inventory.purchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.BillingTerm;
import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.model.admin.User;

public class PurchaseOrderDTO extends BaseDTO {

	private Integer id;
	private Integer businessId;
	private Integer siteId;
	private Integer accountId;
	private Integer chargeDepartmentId;
	private Integer purchaseCurrencyId;
	private Integer supplierId;
	private Integer supplierCountry;
	private Integer billToId;
	private Integer billToCountry;
	private Integer shipToId;
	private Integer shipToCountry;
	private Integer taxId;

	private String code;
	private String statusName;
	private String accountName;
	private String purchaseCurrencyName;
	private String supplierName;
	private String supplierAddress;
	private String supplierCity;
	private String supplierProvince;
	private String supplierPostalCode;
	private String supplierEmail;
	private String supplierCountryName;
	private String billToName;
	private String billToAddress;
	private String billingCity;
	private String billingProvince;
	private String billingPostalCode;
	private String billToCountryName;
	private String shipToName;
	private String shipToAddress;
	private String shippingCity;
	private String shippingProvince;
	private String shippingPostalCode;
	private String shipToCountryName;
	private String chargeDepartmentName;
	private String taxName;

	private User user;

	private Date expectedDeliveryDate;

	private PurchaseOrderStatus purchaseOrderstatus = PurchaseOrderStatus.DRAFT;
	private BillingTerm billingTermId;

	private List<PurchaseOrderAdditionalCostDTO> additionalCostDTOs = new ArrayList<>();
	private List<PurchaseOrderItemDTO> items = new ArrayList<>();
	private List<PurchaseOrderDiscussionDTO> discussionDTOs = new ArrayList<>();
	private List<PurchaseOrderNotificationDTO> notificationDTOs = new ArrayList<>();
	private List<PurchaseOrderChangeLogDTO> purchaseOrderChangeLogDTOs = new ArrayList<>();
	private List<PurchaseOrderFileDTO> purchaseOrderFileDTOs = new ArrayList<>();
	private List<PurchaseOrderTaxDTO> purchaseOrderTaxDTOs = new ArrayList<>();

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

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getChargeDepartmentId() {
		return chargeDepartmentId;
	}

	public void setChargeDepartmentId(Integer chargeDepartmentId) {
		this.chargeDepartmentId = chargeDepartmentId;
	}

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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getPurchaseCurrencyId() {
		return purchaseCurrencyId;
	}

	public void setPurchaseCurrencyId(Integer purchaseCurrencyId) {
		this.purchaseCurrencyId = purchaseCurrencyId;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
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

	public PurchaseOrderStatus getPurchaseOrderstatus() {
		return purchaseOrderstatus;
	}

	public void setPurchaseOrderstatus(PurchaseOrderStatus purchaseOrderstatus) {
		this.purchaseOrderstatus = purchaseOrderstatus;
	}

	public BillingTerm getBillingTermId() {
		return billingTermId;
	}

	public void setBillingTermId(BillingTerm billingTermId) {
		this.billingTermId = billingTermId;
	}

	public List<PurchaseOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<PurchaseOrderItemDTO> items) {
		this.items = items;
	}

	public List<PurchaseOrderAdditionalCostDTO> getAdditionalCostDTOs() {
		return additionalCostDTOs;
	}

	public void setAdditionalCostDTOs(List<PurchaseOrderAdditionalCostDTO> additionalCostDTOs) {
		this.additionalCostDTOs = additionalCostDTOs;
	}

	public List<PurchaseOrderDiscussionDTO> getDiscussionDTOs() {
		return discussionDTOs;
	}

	public void setDiscussionDTOs(List<PurchaseOrderDiscussionDTO> discussionDTOs) {
		this.discussionDTOs = discussionDTOs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public List<PurchaseOrderNotificationDTO> getNotificationDTOs() {
		return notificationDTOs;
	}

	public void setNotificationDTOs(List<PurchaseOrderNotificationDTO> notificationDTOs) {
		this.notificationDTOs = notificationDTOs;
	}

	public List<PurchaseOrderChangeLogDTO> getPurchaseOrderChangeLogDTOs() {
		return purchaseOrderChangeLogDTOs;
	}

	public void setPurchaseOrderChangeLogDTOs(List<PurchaseOrderChangeLogDTO> purchaseOrderChangeLogDTOs) {
		this.purchaseOrderChangeLogDTOs = purchaseOrderChangeLogDTOs;
	}

	public List<PurchaseOrderFileDTO> getPurchaseOrderFileDTOs() {
		return purchaseOrderFileDTOs;
	}

	public void setPurchaseOrderFileDTOs(List<PurchaseOrderFileDTO> purchaseOrderFileDTOs) {
		this.purchaseOrderFileDTOs = purchaseOrderFileDTOs;
	}

	public Integer getTaxId() {
		return taxId;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public List<PurchaseOrderTaxDTO> getPurchaseOrderTaxDTOs() {
		return purchaseOrderTaxDTOs;
	}

	public void setPurchaseOrderTaxDTOs(List<PurchaseOrderTaxDTO> purchaseOrderTaxDTOs) {
		this.purchaseOrderTaxDTOs = purchaseOrderTaxDTOs;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getSupplierCountryName() {
		return supplierCountryName;
	}

	public String getBillToName() {
		return billToName;
	}

	public String getBillToCountryName() {
		return billToCountryName;
	}

	public String getShipToName() {
		return shipToName;
	}

	public String getShipToCountryName() {
		return shipToCountryName;
	}

	public String getChargeDepartmentName() {
		return chargeDepartmentName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setSupplierCountryName(String supplierCountryName) {
		this.supplierCountryName = supplierCountryName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public void setBillToCountryName(String billToCountryName) {
		this.billToCountryName = billToCountryName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public void setShipToCountryName(String shipToCountryName) {
		this.shipToCountryName = shipToCountryName;
	}

	public void setChargeDepartmentName(String chargeDepartmentName) {
		this.chargeDepartmentName = chargeDepartmentName;
	}

	public String getPurchaseCurrencyName() {
		return purchaseCurrencyName;
	}

	public void setPurchaseCurrencyName(String purchaseCurrencyName) {
		this.purchaseCurrencyName = purchaseCurrencyName;
	}

}
