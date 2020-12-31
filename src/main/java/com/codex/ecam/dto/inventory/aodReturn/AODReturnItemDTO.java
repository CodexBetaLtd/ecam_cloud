package com.codex.ecam.dto.inventory.aodReturn;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class AODReturnItemDTO extends BaseDTO {

    private Integer id;

    private Integer aodId;
    private String aodNo;

    private String aodCustomerName;
    private String aodCustomerAddress;

    private Integer aodItemId;
    private Integer aodItemPartId;

    private String aodItemPartNo;
    private BigDecimal returnQty;
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAodItemId() {
        return aodItemId;
    }

    public void setAodItemId(Integer aodItemId) {
        this.aodItemId = aodItemId;
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

    public Integer getAodItemPartId() {
        return aodItemPartId;
    }

    public void setAodItemPartId(Integer aodItemPartId) {
        this.aodItemPartId = aodItemPartId;
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


}
