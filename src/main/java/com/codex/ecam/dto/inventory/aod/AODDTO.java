package com.codex.ecam.dto.inventory.aod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.constants.inventory.AODType;
import com.codex.ecam.dto.BaseDTO;

public class AODDTO extends BaseDTO {

    private Integer id;

    private Integer businessId;
    private Integer businessGroupId;
    private String businessName;

    private Integer siteId;
    private String siteName;

    private String aodNo;
    private Date date = new Date();

    private Integer aodCustomerId;
    private String aodCustomerName;
    private String aodCustomerAddress;
    private String customerContactPerson;

    private Integer requestedUserId;
    private String requestedUserName;

    private Integer woId;
    private String woNo; 

    private Integer statusId;
    private String statusName;
    private AODStatus aodStatus = AODStatus.DRAFT;

    private Integer aodTypeId;
    private String aodTypeName;
    private AODType aodType = AODType.OTHER;

    private String name;
    private String description;

    private List<AODItemDTO> aodItemList = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAodTypeId() {
        return aodTypeId;
    }

    public void setAodTypeId(Integer aodTypeId) {
        this.aodTypeId = aodTypeId;
    }

    public String getAodTypeName() {
        return aodTypeName;
    }

    public void setAodTypeName(String aodTypeName) {
        this.aodTypeName = aodTypeName;
    }

    public String getAodNo() {
        return aodNo;
    }

    public void setAodNo(String aodNo) {
        this.aodNo = aodNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAodCustomerId() {
        return aodCustomerId;
    }

    public void setAodCustomerId(Integer aodCustomerId) {
        this.aodCustomerId = aodCustomerId;
    }

    public String getAodCustomerName() {
        return aodCustomerName;
    }

    public void setAodCustomerName(String aodCustomerName) {
        this.aodCustomerName = aodCustomerName;
    }

    public Integer getRequestedUserId() {
        return requestedUserId;
    }

    public void setRequestedUserId(Integer requestedUserId) {
        this.requestedUserId = requestedUserId;
    }

    public String getRequestedUserName() {
        return requestedUserName;
    }

    public void setRequestedUserName(String requestedUserName) {
        this.requestedUserName = requestedUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getBusinessGroupId() {
        return businessGroupId;
    }

    public void setBusinessGroupId(Integer businessGroupId) {
        this.businessGroupId = businessGroupId;
    }

    public List<AODItemDTO> getAodItemList() {
        return aodItemList;
    }

    public void setAodItemList(List<AODItemDTO> aodItemList) {
        this.aodItemList = aodItemList;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public AODStatus getAodStatus() {
        return aodStatus;
    }

    public void setAodStatus(AODStatus aodStatus) {
        this.aodStatus = aodStatus;
    }

    public AODType getAodType() {
        return aodType;
    }

    public void setAodType(AODType aodType) {
        this.aodType = aodType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAodCustomerAddress() {
        return aodCustomerAddress;
    }

    public void setAodCustomerAddress(String aodCustomerAddress) {
        this.aodCustomerAddress = aodCustomerAddress;
    }

    public String getCustomerContactPerson() {
        return customerContactPerson;
    }

    public void setCustomerContactPerson(String customerContactPerson) {
        this.customerContactPerson = customerContactPerson;
    }

    public Integer getWoId() {
        return woId;
    }

    public void setWoId(Integer woId) {
        this.woId = woId;
    }

    public String getWoNo() {
        return woNo;
    }

    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }


}
