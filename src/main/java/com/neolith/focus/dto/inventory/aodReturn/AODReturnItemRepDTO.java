package com.neolith.focus.dto.inventory.aodReturn;


import com.neolith.focus.dto.BaseReportDTO;

import java.math.BigDecimal;

public class AODReturnItemRepDTO extends BaseReportDTO {

    private String aodNo;
    private String aodCustomer;
    private String aodItemPartNo;
    private String aodItemJobNo;
    private BigDecimal returnQty;
    private String description;

    public String getAodNo() {
        return aodNo;
    }

    public void setAodNo(String aodNo) {
        this.aodNo = aodNo;
    }

    public String getAodCustomer() {
        return aodCustomer;
    }

    public void setAodCustomer(String aodCustomer) {
        this.aodCustomer = aodCustomer;
    }

    public String getAodItemPartNo() {
        return aodItemPartNo;
    }

    public void setAodItemPartNo(String aodItemPartNo) {
        this.aodItemPartNo = aodItemPartNo;
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAodItemJobNo() {
        return aodItemJobNo;
    }

    public void setAodItemJobNo(String aodItemJobNo) {
        this.aodItemJobNo = aodItemJobNo;
    }
}
