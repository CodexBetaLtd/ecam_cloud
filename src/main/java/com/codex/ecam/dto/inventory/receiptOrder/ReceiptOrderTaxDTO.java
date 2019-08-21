package com.codex.ecam.dto.inventory.receiptOrder;

import java.util.Date;

import com.codex.ecam.dto.BaseDTO;

public class ReceiptOrderTaxDTO extends BaseDTO {

    private Integer id;
    private Integer taxId;
    private String taxName;

    private Integer taxTypeId;
    private String taxTypeName;

    private Date fromDate;
    private Date toDate;
    private Double taxRate;
    private Double taxAmount;
    private Double actualTaxAmount;
    private Double definedTaxAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getTaxTypeName() {
        return taxTypeName;
    }

    public void setTaxTypeName(String taxTypeName) {
        this.taxTypeName = taxTypeName;
    }

    public Double getActualTaxAmount() {
        return actualTaxAmount;
    }

    public void setActualTaxAmount(Double actualTaxAmount) {
        this.actualTaxAmount = actualTaxAmount;
    }

    public Double getDefinedTaxAmount() {
        return definedTaxAmount;
    }

    public void setDefinedTaxAmount(Double definedTaxAmount) {
        this.definedTaxAmount = definedTaxAmount;
    }

    public Integer getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Integer taxTypeId) {
        this.taxTypeId = taxTypeId;
    }
}
