package com.neolith.focus.dto.asset;

import com.neolith.focus.constants.WarrantyType;
import com.neolith.focus.constants.WarrantyUsageTermType;
import com.neolith.focus.dto.BaseDTO;
import com.neolith.focus.util.DateUtil;

import java.util.Date;

public class WarrantyDTO extends BaseDTO {
	
	private Integer warrantyId;	
	private Integer warrantyAssetId;
	private Integer warrantyMeterReadingUnitId;	
	private Integer warrantyProviderId;
	private String warrantyAssetName;	
	private String warrantyCertificateNo;
	private String warrantyDescription;
	private String warrantyProviderName;	
	private WarrantyType warrantyType;
	private WarrantyUsageTermType warrantyUsageTermType;	
	private Double warrantyMeterReadingValueLimit;
	private Date warrantyExpiryDate;
	
	
	public Integer getWarrantyId() {
		return warrantyId;
	}
	public void setWarrantyId(Integer warrantyId) {
		this.warrantyId = warrantyId;
	}
	public Integer getWarrantyAssetId() {
		return warrantyAssetId;
	}
	public void setWarrantyAssetId(Integer warrantyAssetId) {
		this.warrantyAssetId = warrantyAssetId;
	}
	public String getWarrantyAssetName() {
		return warrantyAssetName;
	}
	public void setWarrantyAssetName(String warrantyAssetName) {
		this.warrantyAssetName = warrantyAssetName;
	}
	public Integer getWarrantyMeterReadingUnitId() {
		return warrantyMeterReadingUnitId;
	}
	public void setWarrantyMeterReadingUnitId(Integer warrantyMeterReadingUnitId) {
		this.warrantyMeterReadingUnitId = warrantyMeterReadingUnitId;
	}
	public Integer getWarrantyProviderId() {
		return warrantyProviderId;
	}
	public void setWarrantyProviderId(Integer warrantyProviderId) {
		this.warrantyProviderId = warrantyProviderId;
	}
	public String getWarrantyProviderName() {
		return warrantyProviderName;
	}
	public void setWarrantyProviderName(String warrantyProviderName) {
		this.warrantyProviderName = warrantyProviderName;
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
	public Double getWarrantyMeterReadingValueLimit() {
		return warrantyMeterReadingValueLimit;
	}
	public void setWarrantyMeterReadingValueLimit(Double warrantyMeterReadingValueLimit) {
		this.warrantyMeterReadingValueLimit = warrantyMeterReadingValueLimit;
	}
	public String getWarrantyExpiryDate() {
		return DateUtil.getSimpleDateString(warrantyExpiryDate);
	}
	public void setWarrantyExpiryDate(Date warrantyExpiryDate) {
		this.warrantyExpiryDate = warrantyExpiryDate;
	}
	public String getWarrantyCertificateNo() {
		return warrantyCertificateNo;
	}
	public void setWarrantyCertificateNo(String warrantyCertificateNo) {
		this.warrantyCertificateNo = warrantyCertificateNo;
	}
	public String getWarrantyDescription() {
		return warrantyDescription;
	}
	public void setWarrantyDescription(String warrantyDescription) {
		this.warrantyDescription = warrantyDescription;
	}

}
