package com.codex.ecam.model.biz.supplier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name="tbl_supplier")
public class Supplier extends BaseModel {

	private static final long serialVersionUID = -8294657598313148273L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "supplier_s")
	@SequenceGenerator (name = "supplier_s", sequenceName = "supplier_s", allocationSize = 1)
	@Column (name = "id")
	private Integer id;

	@JoinColumn( name = "primary_currency_id" )
	@ManyToOne( targetEntity = Currency.class, fetch = FetchType.LAZY)
	private Currency currency;

	@JoinColumn( name = "country_id" )
	@ManyToOne( targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country country;
	
	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="address")
	private String address;

	@Column(name="city")
	private String city;

	@Column(name="code")
	private String code;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
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

}