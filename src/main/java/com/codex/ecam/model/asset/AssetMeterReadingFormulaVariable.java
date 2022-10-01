package com.codex.ecam.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.MeterReadingUnit;
@Entity
@Table(name = "tbl_asset_meter_reading_formula_variable")
public class AssetMeterReadingFormulaVariable extends BaseModel {
	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tbl_asset_meter_reading_formula_variable_s")
	@SequenceGenerator(name = "tbl_asset_meter_reading_formula_variable_s", sequenceName = "tbl_asset_meter_reading_formula_variable_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "meter_reading_id")
	@ManyToOne(targetEntity = AssetMeterReading.class, fetch = FetchType.LAZY)
	private AssetMeterReading assetMeterReading;
	
	@JoinColumn(name = "meter_reading_unit_id")
	@ManyToOne(targetEntity = MeterReadingUnit.class, fetch = FetchType.LAZY)
	private MeterReadingUnit meterReadingUnit;

	@Column(name = "variable_name")
	private String variableName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public AssetMeterReading getAssetMeterReading() {
		return assetMeterReading;
	}

	public void setAssetMeterReading(AssetMeterReading assetMeterReading) {
		this.assetMeterReading = assetMeterReading;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public MeterReadingUnit getMeterReadingUnit() {
		return meterReadingUnit;
	}

	public void setMeterReadingUnit(MeterReadingUnit meterReadingUnit) {
		this.meterReadingUnit = meterReadingUnit;
	}



}
