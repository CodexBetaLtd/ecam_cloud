package com.codex.ecam.dto.inventory.purchaseOrder;

import com.codex.ecam.constants.PurchaseOrderAdditionalCostType;
import com.codex.ecam.constants.ShippingType;
import com.codex.ecam.dto.BaseDTO;

public class PurchaseOrderAdditionalCostDTO extends BaseDTO {

	private Integer id;

	private Integer additionalCostTypeId;
	private String additionalCostTypeName;
	private PurchaseOrderAdditionalCostType purchaseOrderAdditionalCostType;

	private Integer shippingTypeId;
	private String shippingTypeName;
	private ShippingType shippingType;

	private String additionalCostName;
	private String description;
	private Double amount;
	private Double taxRate;
    private Boolean isOverridePoItemTax;




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdditionalCostTypeId() {
		return additionalCostTypeId;
	}

	public void setAdditionalCostTypeId(Integer additionalCostTypeId) {
		this.additionalCostTypeId = additionalCostTypeId;
	}

	public String getAdditionalCostTypeName() {
		return additionalCostTypeName;
	}

	public void setAdditionalCostTypeName(String additionalCostTypeName) {
		this.additionalCostTypeName = additionalCostTypeName;
	}

	public PurchaseOrderAdditionalCostType getPurchaseOrderAdditionalCostType() {
		return purchaseOrderAdditionalCostType;
	}

	public void setPurchaseOrderAdditionalCostType(PurchaseOrderAdditionalCostType purchaseOrderAdditionalCostType) {
		this.purchaseOrderAdditionalCostType = purchaseOrderAdditionalCostType;
	}

	public Integer getShippingTypeId() {
		return shippingTypeId;
	}

	public void setShippingTypeId(Integer shippingTypeId) {
		this.shippingTypeId = shippingTypeId;
	}

	public String getShippingTypeName() {
		return shippingTypeName;
	}

	public void setShippingTypeName(String shippingTypeName) {
		this.shippingTypeName = shippingTypeName;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	public String getAdditionalCostName() {
		return additionalCostName;
	}

	public void setAdditionalCostName(String additionalCostName) {
		this.additionalCostName = additionalCostName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

    public Boolean getIsOverridePoItemTax() {
        return isOverridePoItemTax;
    }

    public void setIsOverridePoItemTax(Boolean isOverridePoItemTax) {
        this.isOverridePoItemTax = isOverridePoItemTax;
    }


}
