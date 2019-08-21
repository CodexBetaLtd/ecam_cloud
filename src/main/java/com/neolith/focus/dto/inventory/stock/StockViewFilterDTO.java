package com.neolith.focus.dto.inventory.stock;

import com.neolith.focus.dto.BaseDTO;

public class StockViewFilterDTO extends BaseDTO {

    private Integer businessId;
    private String businessName;
    private Integer siteId;
    private String siteName;
    private Integer warehouseId;
    private String warehouseName;
    private Integer itemId;
    private String itemName;
    private String stockNo;

    //delete later ??
    private Integer receiptOrderId;
    private String receiptOrderCode;
    private Integer aodId;
    private String aodCode;
    private Integer aodReturnId;
    private String aodReturnNo;


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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getReceiptOrderId() {
        return receiptOrderId;
    }

    public void setReceiptOrderId(Integer receiptOrderId) {
        this.receiptOrderId = receiptOrderId;
    }

    public String getReceiptOrderCode() {
        return receiptOrderCode;
    }

    public void setReceiptOrderCode(String receiptOrderCode) {
        this.receiptOrderCode = receiptOrderCode;
    }

    public Integer getAodId() {
        return aodId;
    }

    public void setAodId(Integer aodId) {
        this.aodId = aodId;
    }

    public String getAodCode() {
        return aodCode;
    }

    public void setAodCode(String aodCode) {
        this.aodCode = aodCode;
    }

    public Integer getAodReturnId() {
        return aodReturnId;
    }

    public void setAodReturnId(Integer aodReturnId) {
        this.aodReturnId = aodReturnId;
    }

    public String getAodReturnNo() {
        return aodReturnNo;
    }

    public void setAodReturnNo(String aodReturnNo) {
        this.aodReturnNo = aodReturnNo;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
