package com.codex.ecam.dto.inventory.purchaseOrder;

import com.codex.ecam.constants.TAXType;
import com.codex.ecam.dto.BaseDTO;

public class PurchaseOrderTaxDTO extends BaseDTO {

	private Integer id;
	private Integer valueId;
	private Double value;
	private String valueName;
	private Integer order;
	private TAXType taxType;
	
	public Integer getValueId() {
		return valueId;
	}
	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public TAXType getTaxType() {
		return taxType;
	}
	public void setTaxType(TAXType taxType) {
		this.taxType = taxType;
	}


}
