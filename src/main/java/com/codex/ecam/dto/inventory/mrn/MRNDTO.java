package com.codex.ecam.dto.inventory.mrn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.inventory.MRNStatus;
import com.codex.ecam.constants.inventory.MRNType;
import com.codex.ecam.dto.BaseDTO;

public class MRNDTO extends BaseDTO {

    private Integer id;

    private Integer businessId;
    private Integer businessGroupId;
    private String businessName;

    private Integer siteId;
    private String siteName;

    private String mrnNo;
    private Date date = new Date();

    private Integer mrnCustomerId;
    private String mrnCustomerName;
    private String mrnCustomerAddress;
    private String mrnContactPerson;

    private Integer requestedUserId;
    private String requestedUserName;

    private Integer woId;
    private String woNo; 

    private Integer statusId;
    private String statusName;
    private MRNStatus mrnStatus = MRNStatus.DRAFT;
    private MRNType mrnType=MRNType.OTHER;

    private String name;
    private String description;

    private List<MRNItemDTO> mrnItemDTOs = new ArrayList<>();

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

	public Integer getBusinessGroupId() {
		return businessGroupId;
	}

	public void setBusinessGroupId(Integer businessGroupId) {
		this.businessGroupId = businessGroupId;
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

	public String getMrnNo() {
		return mrnNo;
	}

	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getMrnCustomerId() {
		return mrnCustomerId;
	}

	public void setMrnCustomerId(Integer mrnCustomerId) {
		this.mrnCustomerId = mrnCustomerId;
	}

	public String getMrnCustomerName() {
		return mrnCustomerName;
	}

	public void setMrnCustomerName(String mrnCustomerName) {
		this.mrnCustomerName = mrnCustomerName;
	}

	public String getMrnCustomerAddress() {
		return mrnCustomerAddress;
	}

	public void setMrnCustomerAddress(String mrnCustomerAddress) {
		this.mrnCustomerAddress = mrnCustomerAddress;
	}

	public String getMrnContactPerson() {
		return mrnContactPerson;
	}

	public void setMrnContactPerson(String mrnContactPerson) {
		this.mrnContactPerson = mrnContactPerson;
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

	public MRNStatus getMrnStatus() {
		return mrnStatus;
	}

	public void setMrnStatus(MRNStatus mrnStatus) {
		this.mrnStatus = mrnStatus;
	}

	public MRNType getMrnType() {
		return mrnType;
	}

	public void setMrnType(MRNType mrnType) {
		this.mrnType = mrnType;
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

	public List<MRNItemDTO> getMrnItemDTOs() {
		return mrnItemDTOs;
	}

	public void setMrnItemDTOs(List<MRNItemDTO> mrnItemDTOs) {
		this.mrnItemDTOs = mrnItemDTOs;
	}






}
