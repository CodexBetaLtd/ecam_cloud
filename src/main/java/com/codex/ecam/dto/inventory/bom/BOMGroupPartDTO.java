package com.codex.ecam.dto.inventory.bom;

import com.codex.ecam.dto.BaseDTO;

public class BOMGroupPartDTO extends BaseDTO {
	
	private Integer id;
	private Integer partId;
	private Integer bomGroupId;
	
	private Double maxConsumption;
	
	private String partName;
	private String bomGroupName;
	
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
	public Integer getBomGroupId() {
		return bomGroupId;
	}
	public void setBomGroupId(Integer bomGroupId) {
		this.bomGroupId = bomGroupId;
	}
	public Double getMaxConsumption() {
		return maxConsumption;
	}
	public void setMaxConsumption(Double maxConsumption) {
		this.maxConsumption = maxConsumption;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getBomGroupName() {
		return bomGroupName;
	}
	public void setBomGroupName(String bomGroupName) {
		this.bomGroupName = bomGroupName;
	}

}
