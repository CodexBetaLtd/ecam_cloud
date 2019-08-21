package com.neolith.focus.dto.biz.supplier;

import com.neolith.focus.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

public class SupplierDTO extends BaseDTO {

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

    private Integer businessClassificationId;
    private String businessClassificationName;

    private Integer currencyId;
    private String currencyName;
    private String currencySymbol;

    private Integer virtualBusinessId;
    private Integer virtualBusinessOwnerId;
    private String virtualBusinessOwnerName;

    private Boolean active;


    //location
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private Integer countryId;
    private List<SupplierContactDTO> supplierContactDTOs = new ArrayList<>();


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

    public Integer getBusinessClassificationId() {
        return businessClassificationId;
    }

    public void setBusinessClassificationId(Integer businessClassificationId) {
        this.businessClassificationId = businessClassificationId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public List<SupplierContactDTO> getSupplierContactDTOs() {
        return supplierContactDTOs;
    }

    public void setSupplierContactDTOs(List<SupplierContactDTO> supplierContactDTOs) {
        this.supplierContactDTOs = supplierContactDTOs;
    }

    public String getBusinessClassificationName() {
        return businessClassificationName;
    }

    public void setBusinessClassificationName(String businessClassificationName) {
        this.businessClassificationName = businessClassificationName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
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

    public String getVirtualBusinessOwnerName() {
        return virtualBusinessOwnerName;
    }

    public void setVirtualBusinessOwnerName(String virtualBusinessOwnerName) {
        this.virtualBusinessOwnerName = virtualBusinessOwnerName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
