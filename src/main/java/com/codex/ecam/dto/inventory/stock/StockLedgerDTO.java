package com.codex.ecam.dto.inventory.stock;

import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.dto.BaseDTO;

public class StockLedgerDTO extends BaseDTO {

    private Integer id;
    private String itemName;    
    private String businessName;
    private Date date;
    private String description;
    private String stockNo;
    private String grnNo;
    private String aodNo;
    private String aodRetrunNo;
    private String stockAdjustmentNo;
    private String warehouseName;
    private String batchNo;
    private BigDecimal quantityMovement;
    private BigDecimal afterQuantity;
    private BigDecimal beforeQuantity;
    private BigDecimal lastPrice;

    /*================================================*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantityMovement;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantityMovement = quantity;
    }

    public BigDecimal getAfterQuantity() {
        return afterQuantity;
    }

    public void setAfterQuantity(BigDecimal afterQuantity) {
        this.afterQuantity = afterQuantity;
    }

    public BigDecimal getBeforeQuantity() {
        return beforeQuantity;
    }

    public void setBeforeQuantity(BigDecimal beforeQuantity) {
        this.beforeQuantity = beforeQuantity;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getQuantityMovement() {
		return quantityMovement;
	}

	public void setQuantityMovement(BigDecimal quantityMovement) {
		this.quantityMovement = quantityMovement;
	}

	public String getGrnNo() {
		return grnNo;
	}

	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}

	public String getAodNo() {
		return aodNo;
	}

	public void setAodNo(String aodNo) {
		this.aodNo = aodNo;
	}

	public String getAodRetrunNo() {
		return aodRetrunNo;
	}

	public void setAodRetrunNo(String aodRetrunNo) {
		this.aodRetrunNo = aodRetrunNo;
	}

	public String getStockAdjustmentNo() {
		return stockAdjustmentNo;
	}

	public void setStockAdjustmentNo(String stockAdjustmentNo) {
		this.stockAdjustmentNo = stockAdjustmentNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
