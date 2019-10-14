package com.codex.ecam.model.asset;

import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.listeners.asset.AssetMeterReadingLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.MeterReadingUnit;
import com.codex.ecam.model.maintenance.workorder.WorkOrderMeterReading;

@Entity
@Table(name = "tbl_asset_meter_reading")
@EntityListeners({AssetMeterReadingLogListener.class})
public class AssetMeterReading extends BaseModel {

	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_meter_reading_s")
	@SequenceGenerator(name = "asset_meter_reading_s", sequenceName = "asset_meter_reading_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "current_meter_reading_value_id")
	@ManyToOne(targetEntity = AssetMeterReadingValue.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private AssetMeterReadingValue currentAssetMeterReadingValue;

	@JoinColumn(name = "meter_reading_unit_id")
	@ManyToOne(targetEntity = MeterReadingUnit.class, fetch = FetchType.LAZY)
	private MeterReadingUnit meterReadingUnit;

	@Column(name = "meter_reading_name")
	private String meterReadingName;

	@Column(name = "description")
	private String description;

	@Column(name = "avg_meter_reading_value")
	private Double avgMeterReadingValue;
	
	@Column(name = "is_multipel_meter_reading")
	private Boolean isMultipleMeterReading;

	@Column(name = "consumption_formula")
	private String consumptionFormula;
	
	@OneToMany(mappedBy = "assetMeterReading", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetMeterReadingValue> assetMeterReadingValues;
	
	@OneToMany(mappedBy = "assetMeterReading", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetMeterReadingFormulaVariable> formulaVariables;

	@OneToMany(mappedBy = "assetMeterReading", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderMeterReading> workOrderMeterReadings;

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getMeterReadingName() {
		return meterReadingName;
	}

	public void setMeterReadingName(String meterReadingName) {
		this.meterReadingName = meterReadingName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MeterReadingUnit getMeterReadingUnit() {
		return meterReadingUnit;
	}

	public void setMeterReadingUnit(MeterReadingUnit meterReadingUnit) {
		this.meterReadingUnit = meterReadingUnit;
	}

	public Double getAvgMeterReadingValue() {
		return avgMeterReadingValue;
	}

	public void setAvgMeterReadingValue(Double avgMeterReadingValue) {
		this.avgMeterReadingValue = avgMeterReadingValue;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Set<WorkOrderMeterReading> getWorkOrderMeterReadings() {
		return workOrderMeterReadings;
	}

	public void setWorkOrderMeterReadings(Set<WorkOrderMeterReading> workOrderMeterReadings) {
		updateCollection("workOrderMeterReadings", workOrderMeterReadings);
	}

	public AssetMeterReadingValue getCurrentAssetMeterReadingValue() {
		return currentAssetMeterReadingValue;
	}

	public void setCurrentAssetMeterReadingValue(AssetMeterReadingValue currentAssetMeterReadingValue) {
		this.currentAssetMeterReadingValue = currentAssetMeterReadingValue;
	}

	public Set<AssetMeterReadingValue> getAssetMeterReadingValues() {
		return assetMeterReadingValues;
	}

	public void setAssetMeterReadingValues(Set<AssetMeterReadingValue> assetMeterReadingValues) {
		updateCollection("assetMeterReadingValues", assetMeterReadingValues);
	}

	public Boolean getIsMultipleMeterReading() {
		return isMultipleMeterReading;
	}

	public void setIsMultipleMeterReading(Boolean isMultipleMeterReading) {
		this.isMultipleMeterReading = isMultipleMeterReading;
	}



	public Set<AssetMeterReadingFormulaVariable> getFormulaVariables() {
		return formulaVariables;
	}

	public void setFormulaVariables(Set<AssetMeterReadingFormulaVariable> formulaVariables) {
		updateCollection("formulaVariables", formulaVariables);
	}

	public String getConsumptionFormula() {
		return consumptionFormula;
	}

	public void setConsumptionFormula(String consumptionFormula) {
		this.consumptionFormula = consumptionFormula;
	}

	
}
