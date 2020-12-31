package com.codex.ecam.dto.inventory.aodReturn;

import java.util.Date;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class AODReturnFilterDTO extends BaseReportFilterDTO {

    private Integer customerId;
    private String customerName;
    private Integer aodId;
    private String aodNo;
    private String refNo;
    private String returnNo;
    private AODReturnStatus aodReturnStatus;
    private Date aodReturnDate;

    /*==================================================================*/
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

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public AODReturnStatus getAodReturnStatus() {
        return aodReturnStatus;
    }

    public void setAodReturnStatus(AODReturnStatus aodReturnStatus) {
        this.aodReturnStatus = aodReturnStatus;
    }

    public Date getAodReturnDate() {
        return aodReturnDate;
    }

    public void setAodReturnDate(Date aodReturnDate) {
        this.aodReturnDate = aodReturnDate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }
}
