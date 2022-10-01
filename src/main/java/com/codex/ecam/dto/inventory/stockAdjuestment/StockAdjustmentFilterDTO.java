package com.codex.ecam.dto.inventory.stockAdjuestment;


import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class StockAdjustmentFilterDTO extends BaseReportFilterDTO {

    private StockAdjustmentStatus adjustmentStatus;
    private Integer adjustmentStatusId;
    private String adjustmentStatusName;
    private Integer itemId;
    private String itemName;
    private Integer stockId;
    private String stockCode;
    private String adjustmentCode;
//    private Double lastQuantity;
//    private Double newQuantity;


    public StockAdjustmentStatus getAdjustmentStatus() {
        return adjustmentStatus;
    }

    public void setAdjustmentStatus(StockAdjustmentStatus adjustmentStatus) {
        this.adjustmentStatus = adjustmentStatus;
    }

    public Integer getAdjustmentStatusId() {
        return adjustmentStatusId;
    }

    public void setAdjustmentStatusId(Integer adjustmentStatusId) {
        this.adjustmentStatusId = adjustmentStatusId;
    }

    public String getAdjustmentStatusName() {
        return adjustmentStatusName;
    }

    public void setAdjustmentStatusName(String adjustmentStatusName) {
        this.adjustmentStatusName = adjustmentStatusName;
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

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getAdjustmentCode() {
        return adjustmentCode;
    }

    public void setAdjustmentCode(String adjustmentCode) {
        this.adjustmentCode = adjustmentCode;
    }
}
