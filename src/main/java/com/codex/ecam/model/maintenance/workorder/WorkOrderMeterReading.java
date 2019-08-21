package com.codex.ecam.model.maintenance.workorder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.listeners.workorder.WorkOrderMeterReadingLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.AssetMeterReading;

@Entity
@Table(name = "tbl_wo_meter_readings")
@EntityListeners( WorkOrderMeterReadingLogListener.class )
public class WorkOrderMeterReading extends BaseModel {

	private static final long serialVersionUID = -2744127100948902760L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "wo_meter_readings_s")
	@SequenceGenerator(name = "wo_meter_readings_s", sequenceName = "wo_meter_readings_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "work_order_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	@JoinColumn(name = "asset_meter_reading_id")
	@ManyToOne(targetEntity = AssetMeterReading.class, fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	private AssetMeterReading assetMeterReading;

	@Column(name = "description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public AssetMeterReading getAssetMeterReading() {
		return assetMeterReading;
	}

	public void setAssetMeterReading(AssetMeterReading assetMeterReading) {
		this.assetMeterReading = assetMeterReading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
