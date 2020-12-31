package com.codex.ecam.dto.inventory.aod;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class AODItemDTO extends BaseDTO {

    private Integer id;

    private Integer partId;
    private String partName; 

    private Integer warehouseId;
    private String warehouseName;
    
    private Integer mrnItemId;

    private Integer jobId;
    private String jobNo;

    private Integer stockId; 
    private String stockBatchNo; 

    private String description;
    private BigDecimal itemQuantity;
    private BigDecimal itemReturnQuantity;
    private BigDecimal itemCost;
    private BigDecimal remainingQuantity;


    /*=================================================================*/
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

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    } 

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public BigDecimal getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(BigDecimal remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

	public String getStockBatchNo() {
		return stockBatchNo;
	}

	public void setStockBatchNo(String stockBatchNo) {
		this.stockBatchNo = stockBatchNo;
	}

	public Integer getMrnItemId() {
		return mrnItemId;
	}

	public void setMrnItemId(Integer mrnItemId) {
		this.mrnItemId = mrnItemId;
	}
	
	
}
