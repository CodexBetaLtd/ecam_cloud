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
@Table(name = "tbl_asset_meter_reading_consumption")
public class AssetMeterReadingValueConsumption extends BaseModel {
	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tbl_asset_meter_reading_consumption_s")
	@SequenceGenerator(name = "tbl_asset_meter_reading_consumption_s", sequenceName = "tbl_asset_meter_reading_consumption_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "meter_reading_value_id")
	@ManyToOne(targetEntity = AssetMeterReadingValue.class, fetch = FetchType.LAZY)
	private AssetMeterReadingValue assetMeterReadingValue;

	@Column(name = "variable_name")
	private String variableName;
	
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

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
    
    



}
