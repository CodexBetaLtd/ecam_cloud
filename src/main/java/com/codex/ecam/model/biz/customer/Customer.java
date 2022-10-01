package com.codex.ecam.model.biz.customer;

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

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetCustomer;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name="tbl_customer")
public class Customer extends BaseModel {

	private static final long serialVersionUID = 6330478175909499801L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="customer_s")
	@SequenceGenerator(name="customer_s", sequenceName="customer_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn( name="country_id" )
	@ManyToOne( targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country country;

	@Column(name="code")
	private String code;

	@Column(name="name")
	private String name;

	@Column(name="address")
	private String address;

	@Column(name="city")
	private String city;

	@Column(name="province")
	private String province;

	@Column(name="postal_code")
	private String postalCode;

	@Column(name="telephone")
	private String telephone;

	@Column(name="fax")
	private String fax;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<CustomerContact> contacts;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetCustomer> assetCustomer;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, orphanRemoval = true)
	private Set<Asset> currentAssets;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Set<CustomerContact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<CustomerContact> contacts) {
		updateCollection("contacts", contacts);
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Set<AssetCustomer> getAssetCustomer() {
		return assetCustomer;
	}

	public void setAssetCustomer(Set<AssetCustomer> assetCustomer) {
		updateCollection("assetCustomer", assetCustomer);
	}

	public Set<Asset> getCurrentAssets() {
		return currentAssets;
	}

	public void setCurrentAssets(Set<Asset> currentAssets) {
		updateCollection("currentAssets", currentAssets);
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
