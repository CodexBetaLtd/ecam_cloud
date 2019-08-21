package com.neolith.focus.dto.inventory.inventoryGroup;

import java.util.ArrayList;
import java.util.List;

import com.neolith.focus.dto.BaseDTO;

public class InventoryGroupDTO extends BaseDTO {

	private Integer id;

	private Integer businessId;
	private String businessName;

	private Integer siteId;
	private String siteName;

	private String inventoryGroupNo;

	private String name;
	private String description;

	private List<InventoryGroupPartDTO> inventoryGroupPartDTOs = new ArrayList<>();


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getInventoryGroupNo() {
		return inventoryGroupNo;
	}
	public void setInventoryGroupNo(String inventoryGroupNo) {
		this.inventoryGroupNo = inventoryGroupNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<InventoryGroupPartDTO> getInventoryGroupPartDTOs() {
		return inventoryGroupPartDTOs;
	}
	public void setInventoryGroupPartDTOs(List<InventoryGroupPartDTO> inventoryGroupPartDTOs) {
		this.inventoryGroupPartDTOs = inventoryGroupPartDTOs;
	}





}
