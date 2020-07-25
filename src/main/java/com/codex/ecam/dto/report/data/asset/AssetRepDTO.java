package com.codex.ecam.dto.report.data.asset;


import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.BaseReportDTO;

public class AssetRepDTO extends BaseReportDTO {

	private Integer id;
	private String name;
	private String code;
	private String description;
	private String imageLocation; 
	private Boolean isOnline = false;
	private Integer assetCategoryId;
	private Integer businessId;
	private String businessName;
	private String businessAddress;
	private Integer businessTypeId;
	private Integer siteId;
	private String notes;
	private AssetCategoryType assetCategoryType;

	private String assetCategoryName;

	private Integer parentAssetId;
	private String parentAssetName;

	// location
	private String location;
	private String address;
	private String city;
	private String province;
	private String postalCode;
	private Integer countryId;

	private Integer brand;
	private Integer assetCount;
	private String brandName;
	private Integer model;
	private String modelName;
	private String serialNo;

	private Integer customerId;
	private String customerName;
	private String assetUrl;
	private Double assetPrice;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageLocation() {
		return imageLocation;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	public Boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	public Integer getAssetCategoryId() {
		return assetCategoryId;
	}
	public void setAssetCategoryId(Integer assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public AssetCategoryType getAssetCategoryType() {
		return assetCategoryType;
	}
	public void setAssetCategoryType(AssetCategoryType assetCategoryType) {
		this.assetCategoryType = assetCategoryType;
	}
	public String getAssetCategoryName() {
		return assetCategoryName;
	}
	public void setAssetCategoryName(String assetCategoryName) {
		this.assetCategoryName = assetCategoryName;
	}
	public Integer getParentAssetId() {
		return parentAssetId;
	}
	public void setParentAssetId(Integer parentAssetId) {
		this.parentAssetId = parentAssetId;
	}
	public String getParentAssetName() {
		return parentAssetName;
	}
	public void setParentAssetName(String parentAssetName) {
		this.parentAssetName = parentAssetName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getBrand() {
		return brand;
	}
	public void setBrand(Integer brand) {
		this.brand = brand;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAssetUrl() {
		return assetUrl;
	}
	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public Integer getAssetCount() {
		return assetCount;
	}
	public void setAssetCount(Integer assetCount) {
		this.assetCount = assetCount;
	}
	public Double getAssetPrice() {
		return assetPrice;
	}
	public void setAssetPrice(Double assetPrice) {
		this.assetPrice = assetPrice;
	}

	
	

}
