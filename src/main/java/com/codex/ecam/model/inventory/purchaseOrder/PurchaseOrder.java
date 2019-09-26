package com.codex.ecam.model.inventory.purchaseOrder;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.BillingTerm;
import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.listeners.inventory.purchaseorder.PurchaseOrderChangeLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.model.maintenance.ChargeDepartment;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

@Entity
@Table(name = "tbl_purchase_order")
@EntityListeners( { PurchaseOrderChangeLogListener.class } )
public class PurchaseOrder extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_s")
	@SequenceGenerator(name = "purchase_order_s", sequenceName = "purchase_order_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "account_id")
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	private Account account;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "bill_to_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset billToFaciltiy;

	@JoinColumn(name = "charge_department_id")
	@ManyToOne(targetEntity = ChargeDepartment.class, fetch = FetchType.LAZY)
	private ChargeDepartment chargeDepartment;

	@JoinColumn(name = "purchase_currency_id")
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	private Currency purchaseCurrency;

	@JoinColumn(name = "ship_to_country_id")
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country shipToCountry;

	@JoinColumn(name = "ship_to_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset shipToFacility;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn(name = "supplier_country_id")
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country supplierCountry;

	@JoinColumn(name = "bill_to_country_id")
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country billToCountry;

	@JoinColumn(name = "supplier_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business supplier;

	@JoinColumn(name = "vendor_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business vendor;

	@JoinColumn(name = "work_order_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	@Column(name = "purchase_order_status_id")
	private PurchaseOrderStatus purchaseOrderStatus;

	@Column(name = "billing_term_id")
	private BillingTerm billingTermId;

	@Column(name = "code")
	private String code;

	@Column(name = "bill_to_address")
	private String billToAddress;

	@Column(name = "bill_to_city")
	private String billToCity;

	@Column(name = "bill_to_postal_code")
	private String billToPostalCode;

	@Column(name = "bill_to_province")
	private String billToProvince;

	@Column(name = "purchase_order_ref")
	private String purchaseOrderRef;

	@Column(name = "ship_to_address")
	private String shipToAddress;

	@Column(name = "ship_to_city")
	private String shipToCity;

	@Column(name = "ship_to_postal_code")
	private String shipToPostalCode;

	@Column(name = "ship_to_province")
	private String shipToProvince;

	@Column(name = "supplier_address")
	private String supplierAddress;

	@Column(name = "supplier_city")
	private String supplierCity;

	@Column(name = "supplier_postal_code")
	private String supplierPostalCode;

	@Column(name = "supplier_province")
	private String supplierProvince;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "expected_delivery_date")
	private Date expectedDeliveryDate;

	@Column(name = "received_date")
	private Date receivedDate;

	@Column(name = "required_by_date")
	private Date requiredByDate;

	@Column(name = "submitted_date")
	private Date submittedDate;

	@Column(name = "sub_total")
	private Double subTotal;

	@Column(name = "total")
	private Double total;

	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<PurchaseOrderAdditionalCost> purchaseOrderAdditionalCosts;

	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<PurchaseOrderItem> purchaseOrderItems;

	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<PurchaseOrderDiscussion> purchaseOrderDiscussions;

	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<PurchaseOrderNotification> purchaseOrderNotifications ;
	
	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<PurchaseOrderChangeLog> purchaseOrderChangeLogs ;
	
	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<PurchaseOrderFile> purchaseOrderFiles ;




	public List<PurchaseOrderNotification> getPurchaseOrderNotifications() {
		return purchaseOrderNotifications;
	}

	public void setPurchaseOrderNotifications(List<PurchaseOrderNotification> purchaseOrderNotifications) {
		this.purchaseOrderNotifications = purchaseOrderNotifications;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public ChargeDepartment getChargeDepartment() {
		return chargeDepartment;
	}

	public void setChargeDepartment(ChargeDepartment chargeDepartment) {
		this.chargeDepartment = chargeDepartment;
	}

	public Currency getPurchaseCurrency() {
		return purchaseCurrency;
	}

	public void setPurchaseCurrency(Currency purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}

	public Country getShipToCountry() {
		return shipToCountry;
	}

	public void setShipToCountry(Country shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public Country getSupplierCountry() {
		return supplierCountry;
	}

	public void setSupplierCountry(Country supplierCountry) {
		this.supplierCountry = supplierCountry;
	}

	public Business getSupplier() {
		return supplier;
	}

	public void setSupplier(Business supplier) {
		this.supplier = supplier;
	}

	public Business getVendor() {
		return vendor;
	}

	public void setVendor(Business vendor) {
		this.vendor = vendor;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBillToAddress() {
		return billToAddress;
	}

	public void setBillToAddress(String billToAddress) {
		this.billToAddress = billToAddress;
	}

	public String getBillToCity() {
		return billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToPostalCode() {
		return billToPostalCode;
	}

	public void setBillToPostalCode(String billToPostalCode) {
		this.billToPostalCode = billToPostalCode;
	}

	public String getBillToProvince() {
		return billToProvince;
	}

	public void setBillToProvince(String billToProvince) {
		this.billToProvince = billToProvince;
	}

	public String getPurchaseOrderRef() {
		return purchaseOrderRef;
	}

	public void setPurchaseOrderRef(String purchaseOrderRef) {
		this.purchaseOrderRef = purchaseOrderRef;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToPostalCode() {
		return shipToPostalCode;
	}

	public void setShipToPostalCode(String shipToPostalCode) {
		this.shipToPostalCode = shipToPostalCode;
	}

	public String getShipToProvince() {
		return shipToProvince;
	}

	public void setShipToProvince(String shipToProvince) {
		this.shipToProvince = shipToProvince;
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

	public String getSupplierPostalCode() {
		return supplierPostalCode;
	}

	public void setSupplierPostalCode(String supplierPostalCode) {
		this.supplierPostalCode = supplierPostalCode;
	}

	public String getSupplierProvince() {
		return supplierProvince;
	}

	public void setSupplierProvince(String supplierProvince) {
		this.supplierProvince = supplierProvince;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getRequiredByDate() {
		return requiredByDate;
	}

	public void setRequiredByDate(Date requiredByDate) {
		this.requiredByDate = requiredByDate;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Asset getBillToFaciltiy() {
		return billToFaciltiy;
	}

	public void setBillToFaciltiy(Asset billToFaciltiy) {
		this.billToFaciltiy = billToFaciltiy;
	}

	public Asset getShipToFacility() {
		return shipToFacility;
	}

	public void setShipToFacility(Asset shipToFacility) {
		this.shipToFacility = shipToFacility;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public PurchaseOrderStatus getPurchaseOrderStatusId() {
		return purchaseOrderStatus;
	}

	public PurchaseOrderStatus getPurchaseOrderStatus() {
		return purchaseOrderStatus;
	}

    public void setPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

	public BillingTerm getBillingTermId() {
		return billingTermId;
	}

	public void setBillingTermId(BillingTerm billingTermId) {
		this.billingTermId = billingTermId;
	}

	public Country getBillCountry() {
		return billToCountry;
	}

	public void setBillCountry(Country billCountry) {
		this.billToCountry = billCountry;
	}

	public Country getBillToCountry() {
		return billToCountry;
	}

	public void setBillToCountry(Country billToCountry) {
		this.billToCountry = billToCountry;
	}

	public List<PurchaseOrderAdditionalCost> getPurchaseOrderAdditionalCosts() {
		return purchaseOrderAdditionalCosts;
	}

	public void setPurchaseOrderAdditionalCosts(List<PurchaseOrderAdditionalCost> purchaseOrderAdditionalCosts) {
		this.purchaseOrderAdditionalCosts = purchaseOrderAdditionalCosts;
	}

	public List<PurchaseOrderItem> getPurchaseOrderItems() {
		return purchaseOrderItems;
	}

	public void setPurchaseOrderItems(List<PurchaseOrderItem> purchaseOrderItems) {
		this.purchaseOrderItems = purchaseOrderItems;
	}

	public List<PurchaseOrderDiscussion> getPurchaseOrderDiscussions() {
		return purchaseOrderDiscussions;
	}

	public void setPurchaseOrderDiscussions(List<PurchaseOrderDiscussion> purchaseOrderDiscussions) {
		this.purchaseOrderDiscussions = purchaseOrderDiscussions;
	}

	public List<PurchaseOrderChangeLog> getPurchaseOrderChangeLogs() {
		return purchaseOrderChangeLogs;
	}

	public void setPurchaseOrderChangeLogs(List<PurchaseOrderChangeLog> purchaseOrderChangeLogs) {
		this.purchaseOrderChangeLogs = purchaseOrderChangeLogs;
	}

	public List<PurchaseOrderFile> getPurchaseOrderFiles() {
		return purchaseOrderFiles;
	}

	public void setPurchaseOrderFiles(List<PurchaseOrderFile> purchaseOrderFiles) {
		this.purchaseOrderFiles = purchaseOrderFiles;
	}

	
}
