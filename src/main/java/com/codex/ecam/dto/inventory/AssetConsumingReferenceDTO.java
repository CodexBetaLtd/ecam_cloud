package com.codex.ecam.dto.inventory;

import com.codex.ecam.dto.BaseDTO;

public class AssetConsumingReferenceDTO extends BaseDTO {

	private Integer id;
	private Integer assetId;
	private Integer partId;
	private Integer bomGroupPartId;

	private Double maxConsumption;

	private String bomGroupName;
	private String assetName;
	private String partName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Double getMaxConsumption() {
		return maxConsumption;
	}

	public void setMaxConsumption(Double maxConsumption) {
		this.maxConsumption = maxConsumption;
	}

	public String getBomGroupName() {
		return bomGroupName;
	}

	public void setBomGroupName(String bomGroupName) {
		this.bomGroupName = bomGroupName;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public Integer getBomGroupPartId() {
		return bomGroupPartId;
	}

	public void setBomGroupPartId(Integer bomGroupPartId) {
		this.bomGroupPartId = bomGroupPartId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

}
