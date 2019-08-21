package com.neolith.focus.dto.inventory.inventoryGroup;

import com.neolith.focus.dto.BaseDTO;

public class InventoryGroupPartDTO extends BaseDTO {

	private Integer id;
	private Integer partId;
	private String partCode;
	private String partName;
	
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
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}

	
}
