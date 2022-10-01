package com.codex.ecam.model.asset;

import java.util.Date;
import java.util.List;

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

import com.codex.ecam.listeners.asset.AssetMeterReadingValueLogListener;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_asset_meter_reading_value")
@EntityListeners({AssetMeterReadingValueLogListener.class})
public class AssetMeterReadingValue extends BaseModel {

	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tbl_asset_meter_reading_value_s")
	@SequenceGenerator(name = "tbl_asset_meter_reading_value_s", sequenceName = "tbl_asset_meter_reading_value_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_meter_reading_id")
	@ManyToOne(targetEntity = AssetMeterReading.class, fetch = FetchType.LAZY)
	private AssetMeterReading assetMeterReading;

	@Column(name = "meter_reading_value")
	private Double meterReadingValue;

	@Column(name = "added_date")
	private Date addedDate;
	
	@Column(name = "function_string")
	private String functionString;
	
	@OneToMany(mappedBy = "assetMeterReadingValue", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<AssetMeterReadingFormulaValue> assetMeterReadingFormulaValues;

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

	public Double getMeterReadingValue() {
		return meterReadingValue;
	}

	public void setMeterReadingValue(Double meterReadingValue) {
		this.meterReadingValue = meterReadingValue;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public String getFunctionString() {
		return functionString;
	}

	public void setFunctionString(String functionString) {
		this.functionString = functionString;
	}

	public List<AssetMeterReadingFormulaValue> getAssetMeterReadingFormulaValues() {
		return assetMeterReadingFormulaValues;
	}

	public void setAssetMeterReadingFormulaValues(List<AssetMeterReadingFormulaValue> assetMeterReadingFormulaValues) {
		this.assetMeterReadingFormulaValues = assetMeterReadingFormulaValues;
	}



	
}
