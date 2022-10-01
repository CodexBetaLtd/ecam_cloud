package com.codex.ecam.model.asset;

import java.util.Date;

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

import com.codex.ecam.constants.WarrantyType;
import com.codex.ecam.constants.WarrantyUsageTermType;
import com.codex.ecam.listeners.asset.AssetWarrantyLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.MeterReadingUnit;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_warranty")
@EntityListeners({AssetWarrantyLogListener.class})
public class Warranty extends BaseModel {

	private static final long serialVersionUID = -2365164914439437933L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "warranty_s")
	@SequenceGenerator(name = "warranty_s", sequenceName = "warranty_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn( name="asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn( name="meter_reading_unit_id" )
	@ManyToOne( targetEntity = MeterReadingUnit.class, fetch = FetchType.LAZY)
	private MeterReadingUnit meterReadingUnit;

	@JoinColumn( name="provider_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business provider;

	@Column(name="warranty_type")
	private WarrantyType warrantyType;

	@Column(name="warranty_usage_term_type")
	private WarrantyUsageTermType warrantyUsageTermType;

	@Column(name="meter_reading_value_limit")
	private Double meterReadingValueLimit;

	@Column(name="expiry_date")
	private Date expiryDate;

	@Column(name="certificate_no")
	private String certificateNo;

	@Column(name="description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public MeterReadingUnit getMeterReadingUnit() {
		return meterReadingUnit;
	}

	public void setMeterReadingUnit(MeterReadingUnit meterReadingUnit) {
		this.meterReadingUnit = meterReadingUnit;
	}

	public Business getProvider() {
		return provider;
	}

	public void setProvider(Business provider) {
		this.provider = provider;
	}

	public WarrantyType getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(WarrantyType warrantyType) {
		this.warrantyType = warrantyType;
	}

	public WarrantyUsageTermType getWarrantyUsageTermType() {
		return warrantyUsageTermType;
	}

	public void setWarrantyUsageTermType(WarrantyUsageTermType warrantyUsageTermType) {
		this.warrantyUsageTermType = warrantyUsageTermType;
	}

	public Double getMeterReadingValueLimit() {
		return meterReadingValueLimit;
	}

	public void setMeterReadingValueLimit(Double meterReadingValueLimit) {
		this.meterReadingValueLimit = meterReadingValueLimit;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
