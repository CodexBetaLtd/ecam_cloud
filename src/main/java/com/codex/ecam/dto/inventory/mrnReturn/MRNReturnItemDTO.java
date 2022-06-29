package com.codex.ecam.dto.inventory.mrnReturn;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class MRNReturnItemDTO extends BaseDTO {

    private Integer id;

    private Integer partId;
    private String partName; 
    private Integer mrnItemId;

    private String description;
    private BigDecimal itemQuantity;
    private BigDecimal itemReturnQuantity;
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

    public BigDecimal getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(BigDecimal remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

	public Integer getMrnItemId() {
		return mrnItemId;
	}

	public void setMrnItemId(Integer mrnItemId) {
		this.mrnItemId = mrnItemId;
	}

    
}
