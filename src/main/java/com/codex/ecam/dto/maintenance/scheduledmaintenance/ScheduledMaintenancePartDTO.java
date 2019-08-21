package com.codex.ecam.dto.maintenance.scheduledmaintenance;

import com.codex.ecam.dto.BaseDTO;

public class ScheduledMaintenancePartDTO extends BaseDTO {

	private Integer partId;
	private Integer partTaskIndex;
	private Integer partTaskId;
	private String partTaskDescription;;
	private Integer partPartId; //@table_colomn('part_id')
	private String 	partName;
	private String 	partLocation;
	private Integer partScheduledMaintenanceId;
	private Integer partStockId;
	private Double 	partSuggestedQuantity;

	private Integer partTaskAssetId;
	private String partTaskAssetName;

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Integer getPartScheduledMaintenanceId() {
		return partScheduledMaintenanceId;
	}

	public void setPartScheduledMaintenanceId(Integer partScheduledMaintenanceId) {
		this.partScheduledMaintenanceId = partScheduledMaintenanceId;
	}

	public Integer getPartStockId() {
		return partStockId;
	}

	public void setPartStockId(Integer partStockId) {
		this.partStockId = partStockId;
	}

	public Double getPartSuggestedQuantity() {
		return partSuggestedQuantity;
	}

	public void setPartSuggestedQuantity(Double partSuggestedQuantity) {
		this.partSuggestedQuantity = partSuggestedQuantity;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getPartPartId() {
		return partPartId;
	}

	public void setPartPartId(Integer partPartId) {
		this.partPartId = partPartId;
	}

	public String getPartLocation() {
		return partLocation;
	}

	public void setPartLocation(String partLocation) {
		this.partLocation = partLocation;
	}

	public Integer getPartTaskIndex() {
		return partTaskIndex;
	}

	public void setPartTaskIndex(Integer partTaskIndex) {
		this.partTaskIndex = partTaskIndex;
	}

	public Integer getPartTaskId() {
		return partTaskId;
	}

	public void setPartTaskId(Integer partTaskId) {
		this.partTaskId = partTaskId;
	}

	public String getPartTaskDescription() {
		return partTaskDescription;
	}

	public void setPartTaskDescription(String partTaskDescription) {
		this.partTaskDescription = partTaskDescription;
	}

	public Integer getPartTaskAssetId() {
		return partTaskAssetId;
	}

	public void setPartTaskAssetId(Integer partTaskAssetId) {
		this.partTaskAssetId = partTaskAssetId;
	}

	public String getPartTaskAssetName() {
		return partTaskAssetName;
	}

	public void setPartTaskAssetName(String partTaskAssetName) {
		this.partTaskAssetName = partTaskAssetName;
	}

}
