package com.codex.ecam.dto.maintenance.workOrder;

import java.util.Date;

import com.codex.ecam.constants.CostLogType;
import com.codex.ecam.constants.LogType;
import com.codex.ecam.dto.BaseDTO;

public class WorkOrderLogDTO extends BaseDTO {

    private Integer id;
    private String assetName;
    private Integer assetId;
    private String maintenanceTypeName;
    private Integer maintenanceTypeId;
    private Integer workOrderId;
    private String currencyName;
    private Integer currencyId;
    private CostLogType costLogType;
    private LogType logType;
    private Integer stockId;
    private String notes;

    private Date createdDate;
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getMaintenanceTypeName() {
        return maintenanceTypeName;
    }

    public void setMaintenanceTypeName(String maintenanceTypeName) {
        this.maintenanceTypeName = maintenanceTypeName;
    }

    public Integer getMaintenanceTypeId() {
        return maintenanceTypeId;
    }

    public void setMaintenanceTypeId(Integer maintenanceTypeId) {
        this.maintenanceTypeId = maintenanceTypeId;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public CostLogType getCostLogType() {
        return costLogType;
    }

    public void setCostLogType(CostLogType costLogType) {
        this.costLogType = costLogType;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
