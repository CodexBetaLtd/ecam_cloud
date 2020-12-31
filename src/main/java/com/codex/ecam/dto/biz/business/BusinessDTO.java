package com.codex.ecam.dto.biz.business;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.BaseDTO; 

public class BusinessDTO extends BaseDTO {
	
	private Integer id;	
	private String name;	
	private String code;
	
	//general
	private String phone;
	private String phone2;
	private String fax;
	private String webSite;
	private String primaryEmail;
	private String secondaryEmail;
	private String notes;
//	private Integer businessTypeId;
	private Integer businessClassficationId;
	private Integer currencyId; 

    private Integer virtualBusinessId;
    private Integer virtualBusinessOwnerId; 
	
	//location
	private String address;
	private String city;
	private String province;
	private String postalCode;
	private Integer countryId;	
	
	private Boolean roleCustomer; 
	private Boolean roleSupplier;
	private Boolean isFIFO=Boolean.FALSE;
	 
	private List<BusinessContactDTO> businessContactDTOs=new ArrayList<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

//	public Integer getBusinessTypeId() {
//		return businessTypeId;
//	}
	public Integer getBusinessClassficationId() {
		return businessClassficationId;
	}

	//	public void setBusinessTypeId(Integer businessTypeId) {
//		this.businessTypeId = businessTypeId;
//	}
	public void setBusinessClassficationId(Integer businessClassficationId) {
		this.businessClassficationId = businessClassficationId;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public List<BusinessContactDTO> getBusinessContactDTOs() {
		return businessContactDTOs;
	}
	public void setBusinessContactDTOs(List<BusinessContactDTO> businessContactDTOs) {
		this.businessContactDTOs = businessContactDTOs;
	}

	public Integer getVirtualBusinessId() {
		return virtualBusinessId;
	}

	public void setVirtualBusinessId(Integer virtualBusinessId) {
		this.virtualBusinessId = virtualBusinessId;
	}

	public Integer getVirtualBusinessOwnerId() {
		return virtualBusinessOwnerId;
	}

	public void setVirtualBusinessOwnerId(Integer virtualBusinessOwnerId) {
		this.virtualBusinessOwnerId = virtualBusinessOwnerId;
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

	public Boolean getIsFIFO() {
		return isFIFO;
	}

	public void setIsFIFO(Boolean isFIFO) {
		this.isFIFO = isFIFO;
	} 
	
	

}
