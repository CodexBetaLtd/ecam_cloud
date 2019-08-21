package com.codex.ecam.model.admin;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.AssetMeterReading;

@Entity
@Table(name="tbl_meter_reading_unit")
public class MeterReadingUnit extends BaseModel {

	private static final long serialVersionUID = 2795181843338832371L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "meter_reading_unit_s")
	@SequenceGenerator(name = "meter_reading_unit_s", sequenceName = "meter_reading_unit_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="decimles")
	private Double precision;

	@Column(name="symbol")
	private String symbol;

	@OneToMany(mappedBy = "meterReadingUnit", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<AssetMeterReading> assetMeterReadings;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Set<AssetMeterReading> getAssetMeterReadings() {
		return assetMeterReadings;
	}

	public void setAssetMeterReadings(Set<AssetMeterReading> assetMeterReadings) {
		updateCollection("assetMeterReadings", assetMeterReadings);
	}

}
