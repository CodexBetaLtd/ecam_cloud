package com.codex.ecam.dto.inventory.aodReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.dto.BaseDTO;

public class AODReturnDTO extends BaseDTO {

    private Integer id;

    private Integer businessId;
    private String businessName;

    private Integer siteId;
    private String siteName;

    private Integer aodId;
    private String aodNo;
    private String aodCustomerName;
    private String aodCustomerAddress;

    private String returnNo;
    private String returnRefNo;
    private Date returnDate = new Date();
    private String description;

    private Integer statusId;
    private String statusName;
    private AODReturnStatus aodReturnStatus = AODReturnStatus.DRAFT;
    private List<AODReturnItemDTO> aodReturnItemList = new ArrayList<>();

    /*=============================================================================*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getReturnRefNo() {
        return returnRefNo;
    }

    public void setReturnRefNo(String returnRefNo) {
        this.returnRefNo = returnRefNo;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public AODReturnStatus getAodReturnStatus() {
        return aodReturnStatus;
    }

    public void setAodReturnStatus(AODReturnStatus aodReturnStatus) {
        this.aodReturnStatus = aodReturnStatus;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
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

    public Integer getAodId() {
        return aodId;
    }

    public void setAodId(Integer aodId) {
        this.aodId = aodId;
    }

    public String getAodNo() {
        return aodNo;
    }

    public void setAodNo(String aodNo) {
        this.aodNo = aodNo;
    }

    public String getAodCustomerName() {
        return aodCustomerName;
    }

    public void setAodCustomerName(String aodCustomerName) {
        this.aodCustomerName = aodCustomerName;
    }

    public String getAodCustomerAddress() {
        return aodCustomerAddress;
    }

    public void setAodCustomerAddress(String aodCustomerAddress) {
        this.aodCustomerAddress = aodCustomerAddress;
    }

	public List<AODReturnItemDTO> getAodReturnItemList() {
		return aodReturnItemList;
	}

	public void setAodReturnItemList(List<AODReturnItemDTO> aodReturnItemList) {
		this.aodReturnItemList = aodReturnItemList;
	}


}
