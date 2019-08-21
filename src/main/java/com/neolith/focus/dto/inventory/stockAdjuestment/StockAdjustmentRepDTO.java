package com.neolith.focus.dto.inventory.stockAdjuestment;

import com.neolith.focus.constants.inventory.StockAdjustmentStatus;
import com.neolith.focus.dto.BaseReportDTO;

import java.math.BigDecimal;
import java.util.Date;

public class StockAdjustmentRepDTO extends BaseReportDTO {

    private String stockNo;
    private String partName;

    private StockAdjustmentStatus status = StockAdjustmentStatus.DRAFT;
    private Integer statusId;
    private String statusName;

    private String adjustmentCode;
    private String description;
    private BigDecimal lastQuantity;
    private BigDecimal newQuantity;
    private Date date;

    /*================== Getters And Setters =====================*/

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public StockAdjustmentStatus getStatus() {
        return status;
    }

    public void setStatus(StockAdjustmentStatus status) {
        this.status = status;
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

    public String getAdjustmentCode() {
        return adjustmentCode;
    }

    public void setAdjustmentCode(String adjustmentCode) {
        this.adjustmentCode = adjustmentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLastQuantity() {
        return lastQuantity;
    }

    public void setLastQuantity(BigDecimal lastQuantity) {
        this.lastQuantity = lastQuantity;
    }

    public BigDecimal getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
