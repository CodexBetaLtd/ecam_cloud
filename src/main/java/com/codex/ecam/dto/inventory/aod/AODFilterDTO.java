package com.codex.ecam.dto.inventory.aod;

import java.util.Date;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.constants.inventory.AODType;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class AODFilterDTO extends BaseReportFilterDTO {


    private String refNo;
    private String aodNo;
    private AODStatus aodStatus;
    private AODType aodType;
    private Date aodDate;

    private Integer customerId;
    private String customerName;

    private Integer requestedByUserId;
    private String requestedByUserName;

    private Integer jobId;
    private String jobNo;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getAodNo() {
        return aodNo;
    }

    public void setAodNo(String aodNo) {
        this.aodNo = aodNo;
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

    public Date getAodDate() {
        return aodDate;
    }

    public void setAodDate(Date aodDate) {
        this.aodDate = aodDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Integer getRequestedByUserId() {
        return requestedByUserId;
    }

    public void setRequestedByUserId(Integer requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
    }

    public String getRequestedByUserName() {
        return requestedByUserName;
    }

    public void setRequestedByUserName(String requestedByUserName) {
        this.requestedByUserName = requestedByUserName;
    }
}
