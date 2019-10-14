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
@Entity
@Table(name = "tbl_asset_meter_reading_formula_value")
public class AssetMeterReadingFormulaValue extends BaseModel {
	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tbl_asset_meter_reading_formula_value_s")
	@SequenceGenerator(name = "tbl_asset_meter_reading_formula_value_s", sequenceName = "tbl_asset_meter_reading_formula_value_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "meter_reading_value_id")
	@ManyToOne(targetEntity = AssetMeterReadingValue.class, fetch = FetchType.LAZY)
	private AssetMeterReadingValue assetMeterReadingValue;
	
	@JoinColumn(name = "meter_reading_formula_variable_id")
	@ManyToOne(targetEntity = AssetMeterReadingFormulaVariable.class, fetch = FetchType.LAZY)
	private AssetMeterReadingFormulaVariable assetMeterReadingFormulaVariable;
	
	@Column(name = "value")
	private Double value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssetMeterReadingValue getAssetMeterReadingValue() {
		return assetMeterReadingValue;
	}

	public void setAssetMeterReadingValue(AssetMeterReadingValue assetMeterReadingValue) {
		this.assetMeterReadingValue = assetMeterReadingValue;
	}


	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public AssetMeterReadingFormulaVariable getAssetMeterReadingFormulaVariable() {
		return assetMeterReadingFormulaVariable;
	}

	public void setAssetMeterReadingFormulaVariable(AssetMeterReadingFormulaVariable assetMeterReadingFormulaVariable) {
		this.assetMeterReadingFormulaVariable = assetMeterReadingFormulaVariable;
	}


    



}
