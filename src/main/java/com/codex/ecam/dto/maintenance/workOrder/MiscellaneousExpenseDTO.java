package com.codex.ecam.dto.maintenance.workOrder;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class MiscellaneousExpenseDTO extends BaseDTO {

    private Integer id;
    private Integer miscellaneousExpenseTypeId;
    private String miscellaneousExpenseTypeDescription;
    private String description;

    private BigDecimal estimatedUnitCost;
    private BigDecimal estimatedQuantity;
    private BigDecimal estimatedTotalCost;
    private BigDecimal actualUnitCost;
    private BigDecimal actualQuantity;
    private BigDecimal actualTotalCost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEstimatedUnitCost() {
        return estimatedUnitCost;
    }

    public void setEstimatedUnitCost(BigDecimal estimatedUnitCost) {
        this.estimatedUnitCost = estimatedUnitCost;
    }

    public BigDecimal getEstimatedQuantity() {
        return estimatedQuantity;
    }

    public void setEstimatedQuantity(BigDecimal estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
    }

    public BigDecimal getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public void setEstimatedTotalCost(BigDecimal estimatedTotalCost) {
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public BigDecimal getActualUnitCost() {
        return actualUnitCost;
    }

    public void setActualUnitCost(BigDecimal actualUnitCost) {
        this.actualUnitCost = actualUnitCost;
    }

    public BigDecimal getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(BigDecimal actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public BigDecimal getActualTotalCost() {
        return actualTotalCost;
    }

    public void setActualTotalCost(BigDecimal actualTotalCost) {
        this.actualTotalCost = actualTotalCost;
    }

    public Integer getMiscellaneousExpenseTypeId() {
        return miscellaneousExpenseTypeId;
    }

    public void setMiscellaneousExpenseTypeId(Integer miscellaneousExpenseTypeId) {
        this.miscellaneousExpenseTypeId = miscellaneousExpenseTypeId;
    }

    public String getMiscellaneousExpenseTypeDescription() {
        return miscellaneousExpenseTypeDescription;
    }

    public void setMiscellaneousExpenseTypeDescription(String miscellaneousExpenseTypeDescription) {
        this.miscellaneousExpenseTypeDescription = miscellaneousExpenseTypeDescription;
    }
}
