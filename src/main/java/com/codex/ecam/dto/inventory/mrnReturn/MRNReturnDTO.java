package com.codex.ecam.dto.inventory.mrnReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.inventory.MRNReturnStatus;
import com.codex.ecam.dto.BaseDTO;

public class MRNReturnDTO extends BaseDTO {

    private Integer id;

    private Integer businessId;
    private Integer businessGroupId;
    private String businessName;

    private Integer siteId;
    private String siteName;

    private String mrnReturnNo;
    private String mrnNo;
    private Integer mrnId;
    private Date date = new Date();

    private Integer requestedUserId;
    private String requestedUserName;
    
    private Integer statusId;
    private String statusName;
    private MRNReturnStatus mrnReturnStatus = MRNReturnStatus.DRAFT;

    private String name;
    private String description;

    private List<MRNReturnItemDTO> mrnReturnItemDTOs = new ArrayList<>();

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


	public String getMrnReturnNo() {
		return mrnReturnNo;
	}

	public void setMrnReturnNo(String mrnReturnNo) {
		this.mrnReturnNo = mrnReturnNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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


	public MRNReturnStatus getMrnReturnStatus() {
		return mrnReturnStatus;
	}

	public void setMrnReturnStatus(MRNReturnStatus mrnReturnStatus) {
		this.mrnReturnStatus = mrnReturnStatus;
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

	public List<MRNReturnItemDTO> getMrnReturnItemDTOs() {
		return mrnReturnItemDTOs;
	}

	public void setMrnReturnItemDTOs(List<MRNReturnItemDTO> mrnReturnItemDTOs) {
		this.mrnReturnItemDTOs = mrnReturnItemDTOs;
	}

	public String getMrnNo() {
		return mrnNo;
	}

	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}

	public Integer getMrnId() {
		return mrnId;
	}

	public void setMrnId(Integer mrnId) {
		this.mrnId = mrnId;
	}


}
