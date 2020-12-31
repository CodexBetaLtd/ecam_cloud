package com.codex.ecam.dto.inventory.mrn;


import java.math.BigDecimal;

public class MRNItemRepDTO {

    private String siteName;
    private String partName;
    private String stockNo;
    private String description;
    private String batchNo;
    private String jobNo;
    private BigDecimal itemQuantity;
    private BigDecimal itemReturnQuantity;


    /*========================================*/

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public BigDecimal getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(BigDecimal itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public BigDecimal getItemReturnQuantity() {
        return itemReturnQuantity;
    }

    public void setItemReturnQuantity(BigDecimal itemReturnQuantity) {
        this.itemReturnQuantity = itemReturnQuantity;
    }
}
