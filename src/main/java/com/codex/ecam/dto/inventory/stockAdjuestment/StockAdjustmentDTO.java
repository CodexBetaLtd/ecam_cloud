package com.codex.ecam.dto.inventory.stockAdjuestment;

import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.dto.BaseDTO;

public class StockAdjustmentDTO extends BaseDTO {

    private Integer id;

    private Integer partId;
    private String partName;

    private String warehouseName;
    private Integer warehouseId;

    private String stockNo;
    private Integer stockId;

    private StockAdjustmentStatus adjustmentStatus = StockAdjustmentStatus.DRAFT;
    private Integer adjustmentStatusId;
    private String adjustmentStatusName;

    private String adjustmentCode;
    private String description;
    private BigDecimal lastQuantity;
    private BigDecimal newQuantity;

    private Date stockAdjustmentDate = new Date();
    
    

    /*================== Getters And Setters =====================*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
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

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

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

    public String getAdjustmentCode() {
        return adjustmentCode;
    }

    public void setAdjustmentCode(String adjustmentCode) {
        this.adjustmentCode = adjustmentCode;
    }

    public Date getStockAdjustmentDate() {
        return stockAdjustmentDate;
    }

    public void setStockAdjustmentDate(Date stockAdjustmentDate) {
        this.stockAdjustmentDate = stockAdjustmentDate;
    }


}
