package com.codex.ecam.model.biz.business;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.model.asset.Asset;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="tbl_business")
public class Business extends BaseModel {

	private static final long serialVersionUID = -8294657598313148273L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "business_s")
	@SequenceGenerator (name = "business_s", sequenceName = "business_s", allocationSize = 1)
	@Column (name = "id")
	private Integer id;

	@JoinColumn( name = "business_classification_id" )
	@ManyToOne( targetEntity = BusinessClassification.class, fetch = FetchType.LAZY)
	private BusinessClassification businessClassification;

	@JoinColumn( name = "business_type_id" )
	@ManyToOne( targetEntity = BusinessTypeDefinition.class, fetch = FetchType.LAZY)
	private BusinessTypeDefinition businessTypeDefinition;

	@JoinColumn( name = "primary_currency_id" )
	@ManyToOne( targetEntity = Currency.class, fetch = FetchType.LAZY)
	private Currency currency;

	@JoinColumn( name = "country_id" )
	@ManyToOne( targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country country;

	@JoinColumn( name = "virtual_business_id" )
	@ManyToOne( targetEntity = BusinessVirtual.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private BusinessVirtual businessVirtual;

	@Column(name="default_company")
	private Boolean defaultCompany;

	@Column(name="group_quotes")
	private Boolean groupQuotes;

	@Column(name="our_business")
	private Boolean ourBusiness;

	@Column(name="preferred_business")
	private Boolean preferredBusiness;

	@Column(name="public_community")
	private Boolean publicCommunity;

	@Column(name="is_focus_source_vendor")
	private Boolean isFocusSourceVendor;

	@Column(name="last_scheduled_maintenance_run_time")
	private Date lastScheduledMaintenanceRunTime;

	@Column(name="sys_code")
	private Integer systemCode;

	@Column(name="focus_source_seller_id")
	private Integer focusSourceSellerId;

	@Column(name="address")
	private String address;

	@Column(name="business_corp_id")
	private String businessCorpId;

	@Column(name="city")
	private String city;

	@Column(name="code")
	private String code;

	@Column(name="community_password")
	private String communityPassword;

	@Column(name="community_private_key")
	private String communityPrivateKey;

	@Column(name="fax")
	private String fax;

	@Column(name="name")
	private String name;

	@Column(name="notes")
	private String notes;

	@Column(name="phone")
	private String phone;

	@Column(name="phone2")
	private String phone2;

	@Column(name="postal_code")
	private String postalcode;

	@Column(name="primary_contact")
	private String primaryContact;

	@Column(name="primary_email")
	private String primaryEmail;

	@Column(name="secondary_email")
	private String secondaryEmail;

	@Column(name="province")
	private String province;

	@Column(name="timezone")
	private String timezone;

	@Column(name="web_site")
	private String webSite;

	@Column(name = "virtual_business")
	private Boolean virtualBusiness;

	@Column(name = "role_customer")
	private Boolean roleCustomer = false;

	@Column(name = "role_supplier")
	private Boolean roleSupplier = false;
	
	@Column(name = "is_fifo")
	private Boolean isFIFO = false;

	@OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<Asset> assets;

	@OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BusinessApp> businessApps;
	
	@OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<BusinessContact> contacts;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, orphanRemoval = true)
	private Set<Asset> currentAssets;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getDefaultCompany() {
		return defaultCompany;
	}

	public void setDefaultCompany(Boolean defaultCompany) {
		this.defaultCompany = defaultCompany;
	}

	public Boolean getGroupQuotes() {
		return groupQuotes;
	}

	public void setGroupQuotes(Boolean groupQuotes) {
		this.groupQuotes = groupQuotes;
	}

	public Boolean getOurBusiness() {
		return ourBusiness;
	}

	public void setOurBusiness(Boolean ourBusiness) {
		this.ourBusiness = ourBusiness;
	}

	public Boolean getPreferredBusiness() {
		return preferredBusiness;
	}

	public void setPreferredBusiness(Boolean preferredBusiness) {
		this.preferredBusiness = preferredBusiness;
	}

	public Boolean getPublicCommunity() {
		return publicCommunity;
	}

	public void setPublicCommunity(Boolean publicCommunity) {
		this.publicCommunity = publicCommunity;
	}

	public Boolean getIsFocusSourceVendor() {
		return isFocusSourceVendor;
	}

	public void setIsFocusSourceVendor(Boolean isFocusSourceVendor) {
		this.isFocusSourceVendor = isFocusSourceVendor;
	}

	public Date getLastScheduledMaintenanceRunTime() {
		return lastScheduledMaintenanceRunTime;
	}

	public void setLastScheduledMaintenanceRunTime(Date lastScheduledMaintenanceRunTime) {
		this.lastScheduledMaintenanceRunTime = lastScheduledMaintenanceRunTime;
	}

	public Integer getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(Integer systemCode) {
		this.systemCode = systemCode;
	}

	public Integer getFocusSourceSellerId() {
		return focusSourceSellerId;
	}

	public void setFocusSourceSellerId(Integer focusSourceSellerId) {
		this.focusSourceSellerId = focusSourceSellerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessCorpId() {
		return businessCorpId;
	}

	public void setBusinessCorpId(String businessCorpId) {
		this.businessCorpId = businessCorpId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCommunityPassword() {
		return communityPassword;
	}

	public void setCommunityPassword(String communityPassword) {
		this.communityPassword = communityPassword;
	}

	public String getCommunityPrivateKey() {
		return communityPrivateKey;
	}

	public void setCommunityPrivateKey(String communityPrivateKey) {
		this.communityPrivateKey = communityPrivateKey;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public BusinessTypeDefinition getBusinessTypeDefinition() {
		return businessTypeDefinition;
	}

	public void setBusinessTypeDefinition(BusinessTypeDefinition businessTypeDefinition) {
		this.businessTypeDefinition = businessTypeDefinition;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public Set<BusinessApp> getBusinessApps() {
		return businessApps;
	}

	public void setBusinessApps(Set<BusinessApp> businessApps) {
		updateCollection("businessApps", businessApps);
	}

	public Set<Asset> getAssets() {
		return assets;
	}

	public void setAssets(Set<Asset> assets) {
		updateCollection("assets", assets);
	}

	public Boolean getVirtualBusiness() {
		return virtualBusiness;
	}

	public void setVirtualBusiness(Boolean virtualBusiness) {
		this.virtualBusiness = virtualBusiness;
	}

	public Boolean getRoleCustomer() {
		return roleCustomer;
	}

	public void setRoleCustomer(Boolean roleCustomer) {
		this.roleCustomer = roleCustomer;
	}

	public Boolean getRoleSupplier() {
		return roleSupplier;
	}

	public void setRoleSupplier(Boolean roleSupplier) {
		this.roleSupplier = roleSupplier;
	}

	public BusinessVirtual getBusinessVirtual() {
		return businessVirtual;
	}

	public void setBusinessVirtual(BusinessVirtual businessVirtual) {
		this.businessVirtual = businessVirtual;
	}

	public BusinessClassification getBusinessClassification() {
		return businessClassification;
	}

	public void setBusinessClassification(BusinessClassification businessClassification) {
		this.businessClassification = businessClassification;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	

	public Set<Asset> getCurrentAssets() {
		return currentAssets;
	}

	public void setCurrentAssets(Set<Asset> currentAssets) {
		this.currentAssets = currentAssets;
	}

	public Set<BusinessContact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<BusinessContact> contacts) {
		this.contacts = contacts;
	}

	public Boolean getIsFIFO() {
		return isFIFO;
	}

	public void setIsFIFO(Boolean isFIFO) {
		this.isFIFO = isFIFO;
	}
	
	
}
