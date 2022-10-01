package com.codex.ecam.dto.inventory.mrnReturn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;

public class MRNRepDTO extends BaseReportDTO {

    private String aodNo;
    private Date date;
    private String aodJobNo;

    private String preparedBy;
    private String authorisedBy;
    private String requestedUserName;

    private String customerNo;
    private String customerName;
    private String customerAddressLine01;
    private String customerAddressLine02;
    private String customerAddressLine03;
    private String customerContactPerson;

    private String workOrderNo;     //TODO: remove
    private String aodStatus;
    private String aodType;
    private List<MRNItemRepDTO> mrnItemRepDTOs = new ArrayList<>();


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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRequestedUserName() {
        return requestedUserName;
    }

    public void setRequestedUserName(String requestedUserName) {
        this.requestedUserName = requestedUserName;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getAodStatus() {
        return aodStatus;
    }

    public void setAodStatus(String aodStatus) {
        this.aodStatus = aodStatus;
    }

    public String getAodType() {
        return aodType;
    }

    public void setAodType(String aodType) {
        this.aodType = aodType;
    }



    public List<MRNItemRepDTO> getMrnItemRepDTOs() {
		return mrnItemRepDTOs;
	}

	public void setMrnItemRepDTOs(List<MRNItemRepDTO> mrnItemRepDTOs) {
		this.mrnItemRepDTOs = mrnItemRepDTOs;
	}

	public String getCustomerAddressLine01() {
        return customerAddressLine01;
    }

    public void setCustomerAddressLine01(String customerAddressLine01) {
        this.customerAddressLine01 = customerAddressLine01;
    }

    public String getCustomerAddressLine02() {
        return customerAddressLine02;
    }

    public void setCustomerAddressLine02(String customerAddressLine02) {
        this.customerAddressLine02 = customerAddressLine02;
    }

    public String getCustomerAddressLine03() {
        return customerAddressLine03;
    }

    public void setCustomerAddressLine03(String customerAddressLine03) {
        this.customerAddressLine03 = customerAddressLine03;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerContactPerson() {
        return customerContactPerson;
    }

    public void setCustomerContactPerson(String customerContactPerson) {
        this.customerContactPerson = customerContactPerson;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(String authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public String getAodJobNo() {
        return aodJobNo;
    }

    public void setAodJobNo(String aodJobNo) {
        this.aodJobNo = aodJobNo;
    }
}
