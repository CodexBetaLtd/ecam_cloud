package com.codex.ecam.model.inventory.rfq;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_rfq")
public class RFQ extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_s")
	@SequenceGenerator(name = "rfq_s", sequenceName = "rfq_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "bill_to_country_id")
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country billingCountry;

	@JoinColumn(name = "ship_to_country_id")
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country shippingCountry;

	@JoinColumn(name = "supplier_country_id")
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country supplierCountry;

	@JoinColumn(name = "bill_to_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset billTo;

	@JoinColumn(name = "ship_to_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset shipTo;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn(name = "asset_business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "asset_business_supplier_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business supplierBusiness;

	@Column(name = "bill_to_address")
	private String billingAddress;

	@Column(name = "bill_to_city")
	private String billingCity;

	@Column(name = "bill_to_postal_code")
	private String billingPostalCode;

	@Column(name = "bill_to_province")
	private String billingProvince;

	@Column(name = "message_content")
	private String messageContent;

	@Column(name = "message_subject")
	private String messageSubject;

	@Column(name = "quote_reference_number")
	private String quoteReferenceNumber;

	@Column(name = "ship_to_address")
	private String shippingAddress;

	@Column(name = "ship_to_city")
	private String shippingCity;

	@Column(name = "ship_to_postal_code")
	private String shippingPostalCode;

	@Column(name = "ship_to_province")
	private String shippingProvince;

	@Column(name = "supplier_to_address")
	private String supplierAddress;

	@Column(name = "supplier_to_city")
	private String supplierCity;

	@Column(name = "supplier_to_postal_code")
	private String supplierPostalCode;

	@Column(name = "supplier_to_province")
	private String supplierProvince;

	@Column(name = "code")
	private String code;

	@Column(name = "date_expected_delivery")
	private Date dateExpectedDelivery;

	@Column(name = "date_required_response")
	private Date dateRequiredResponse;

	@Column(name = "date_sent")
	private Date dateSent;

	@Column(name = "rfq_status_id")
	private RFQStatus rfqStatus;

	@OneToMany(mappedBy = "rfq", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<RFQItem> rfqItems;
	
	@OneToMany(mappedBy = "rfq", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<RFQFile> rfqFiles;
	
	@OneToMany(mappedBy = "rfq", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<RFQNotification> rfqNotifications;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingPostalCode() {
		return billingPostalCode;
	}

	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}

	public String getBillingProvince() {
		return billingProvince;
	}

	public void setBillingProvince(String billingProvince) {
		this.billingProvince = billingProvince;
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

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingPostalCode() {
		return shippingPostalCode;
	}

	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}

	public String getShippingProvince() {
		return shippingProvince;
	}

	public void setShippingProvince(String shippingProvince) {
		this.shippingProvince = shippingProvince;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDateExpectedDelivery() {
		return dateExpectedDelivery;
	}

	public void setDateExpectedDelivery(Date dateExpectedDelivery) {
		this.dateExpectedDelivery = dateExpectedDelivery;
	}

	public Date getDateRequiredResponse() {
		return dateRequiredResponse;
	}

	public void setDateRequiredResponse(Date dateRequiredResponse) {
		this.dateRequiredResponse = dateRequiredResponse;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Country getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(Country billingCountry) {
		this.billingCountry = billingCountry;
	}

	public Country getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(Country shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public Country getSupplierCountry() {
		return supplierCountry;
	}

	public void setSupplierCountry(Country supplierCountry) {
		this.supplierCountry = supplierCountry;
	}

	public Asset getBillTo() {
		return billTo;
	}

	public void setBillTo(Asset billTo) {
		this.billTo = billTo;
	}

	public Asset getShipTo() {
		return shipTo;
	}

	public void setShipTo(Asset shipTo) {
		this.shipTo = shipTo;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public RFQStatus getRfqStatus() {
		return rfqStatus;
	}

	public void setRfqStatus(RFQStatus rfqStatus) {
		this.rfqStatus = rfqStatus;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Business getSupplierBusiness() {
		return supplierBusiness;
	}

	public void setSupplierBusiness(Business supplierBusiness) {
		this.supplierBusiness = supplierBusiness;
	}

	public List<RFQItem> getRfqItems() {
		return rfqItems;
	}

	public void setRfqItems(List<RFQItem> rfqItems) {
		this.rfqItems = rfqItems;
	}

	public Set<RFQFile> getRfqFiles() {
		return rfqFiles;
	}

	public void setRfqFiles(Set<RFQFile> rfqFiles) {
		this.rfqFiles = rfqFiles;
	}

	public Set<RFQNotification> getRfqNotifications() {
		return rfqNotifications;
	}

	public void setRfqNotifications(Set<RFQNotification> rfqNotifications) {
		this.rfqNotifications = rfqNotifications;
	}

	
	
}
