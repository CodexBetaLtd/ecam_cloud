package com.codex.ecam.dto.maintenance.workOrder;

import com.codex.ecam.dto.BaseDTO;

public class WorkOrderPartDTO extends BaseDTO {

	private Integer woPartId;

	private Integer woPartTaskId;
	private Integer woPartTaskIndex;
	private String woPartTaskDescription;

	private Integer woPartAssetId;
	private String woPartAssetName;
	private Integer woPartPartId; // @table_colomn('part_id')
	private String woPartPartName;
	private String woPartLocation;
	private Integer woPartWorkOrderId;
	private Integer woPartStockId;
	private Double woPartEstimatedQuantity;
	private Double woPartActualQuantity;
	private String woPartRemark;

	public Integer getWoPartId() {
		return woPartId;
	}

	public void setWoPartId(Integer woPartId) {
		this.woPartId = woPartId;
	}

	public Integer getWoPartAssetId() {
		return woPartAssetId;
	}

	public void setWoPartAssetId(Integer woPartAssetId) {
		this.woPartAssetId = woPartAssetId;
	}

	public String getWoPartAssetName() {
		return woPartAssetName;
	}

	public void setWoPartAssetName(String woPartAssetName) {
		this.woPartAssetName = woPartAssetName;
	}

	public Integer getWoPartPartId() {
		return woPartPartId;
	}

	public void setWoPartPartId(Integer woPartPartId) {
		this.woPartPartId = woPartPartId;
	}

	public String getWoPartPartName() {
		return woPartPartName;
	}

	public void setWoPartPartName(String woPartPartName) {
		this.woPartPartName = woPartPartName;
	}

	public String getWoPartLocation() {
		return woPartLocation;
	}

	public void setWoPartLocation(String woPartLocation) {
		this.woPartLocation = woPartLocation;
	}

	public Integer getWoPartWorkOrderId() {
		return woPartWorkOrderId;
	}

	public void setWoPartWorkOrderId(Integer woPartWorkOrderId) {
		this.woPartWorkOrderId = woPartWorkOrderId;
	}

	public Integer getWoPartStockId() {
		return woPartStockId;
	}

	public void setWoPartStockId(Integer woPartStockId) {
		this.woPartStockId = woPartStockId;
	}

	public Double getWoPartEstimatedQuantity() {
		return woPartEstimatedQuantity;
	}

	public void setWoPartEstimatedQuantity(Double woPartEstimatedQuantity) {
		this.woPartEstimatedQuantity = woPartEstimatedQuantity;
	}

	public Double getWoPartActualQuantity() {
		return woPartActualQuantity;
	}

	public void setWoPartActualQuantity(Double woPartActualQuantity) {
		this.woPartActualQuantity = woPartActualQuantity;
	}

	public String getWoPartRemark() {
		return woPartRemark;
	}

	public void setWoPartRemark(String woPartRemark) {
		this.woPartRemark = woPartRemark;
	}

	public Integer getWoPartTaskId() {
		return woPartTaskId;
	}

	public void setWoPartTaskId(Integer woPartTaskId) {
		this.woPartTaskId = woPartTaskId;
	}

	public Integer getWoPartTaskIndex() {
		return woPartTaskIndex;
	}

	public void setWoPartTaskIndex(Integer woPartTaskIndex) {
		this.woPartTaskIndex = woPartTaskIndex;
	}

	public String getWoPartTaskDescription() {
		return woPartTaskDescription;
	}

	public void setWoPartTaskDescription(String woPartTaskDescription) {
		this.woPartTaskDescription = woPartTaskDescription;
	}

}
