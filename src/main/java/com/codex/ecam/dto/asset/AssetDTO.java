package com.codex.ecam.dto.asset;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.inventory.AssetConsumingReferenceDTO;

public class AssetDTO extends BaseDTO {

	private Integer id;
	private String name;
	private String code;
	private String description;
	private String imageLocation; 
	private Boolean isOnline = false;
	private Integer assetCategoryId;
	private Integer businessId;
	private String businessName;
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
	private String brandName;
	private Integer model;
	private String modelName;
	private String serialNo;

	private Integer customerId;
	private String customerName;

	private Integer currentAssetEventId;

	private AssetPurchasingDTO assetPurchasingDetail = new AssetPurchasingDTO();
	private LocationDTO locationDTO = new LocationDTO();

	private List<AssetMeterReadingDTO> assetMeterReadings = new ArrayList<>();
	private List<AssetMeterReadingValueDTO> assetMeterReadingValues = new ArrayList<>();
	private List<AssetEventTypeAssetDTO> assetEventTypeAssets = new ArrayList<>();
	private List<AssetEventDTO> assetEvents = new ArrayList<>();
	private List<AssetUserDTO> assetUserDTOs = new ArrayList<>();
	private List<WarrantyDTO> warranties = new ArrayList<>();
	private List<AssetConsumingReferenceDTO> assetConsumeRefs = new ArrayList<>();
	private List<AssetConsumingReferenceDTO> partConsumeRefs = new ArrayList<>();
	private List<AssetFileDTO> assetFileDTOs = new ArrayList<>();
	private List<AssetDTO> children = new ArrayList<>();
	
	private Integer childCount;

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

	public Integer getAssetCategoryId() {
		return assetCategoryId;
	}

	public void setAssetCategoryId(Integer assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
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

	public Integer getParentAssetId() {
		return parentAssetId;
	}

	public void setParentAssetId(Integer parentAssetId) {
		this.parentAssetId = parentAssetId;
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

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getCurrentAssetEventId() {
		return currentAssetEventId;
	}

	public void setCurrentAssetEventId(Integer currentAssetEventId) {
		this.currentAssetEventId = currentAssetEventId;
	}

	public String getAssetCategoryName() {
		return assetCategoryName;
	}

	public void setAssetCategoryName(String assetCategoryName) {
		this.assetCategoryName = assetCategoryName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<AssetUserDTO> getAssetUserDTOs() {
		return assetUserDTOs;
	}

	public void setAssetUserDTOs(List<AssetUserDTO> assetUserDTOs) {
		this.assetUserDTOs = assetUserDTOs;
	}

	public List<WarrantyDTO> getWarranties() {
		return warranties;
	}

	public void setWarranties(List<WarrantyDTO> warranties) {
		this.warranties = warranties;
	}

	public List<AssetConsumingReferenceDTO> getAssetConsumeRefs() {
		return assetConsumeRefs;
	}

	public void setAssetConsumeRefs(List<AssetConsumingReferenceDTO> assetConsumeRefs) {
		this.assetConsumeRefs = assetConsumeRefs;
	}

	public List<AssetConsumingReferenceDTO> getPartConsumeRefs() {
		return partConsumeRefs;
	}

	public void setPartConsumeRefs(List<AssetConsumingReferenceDTO> partConsumeRefs) {
		this.partConsumeRefs = partConsumeRefs;
	}

	public AssetPurchasingDTO getAssetPurchasingDetail() {
		return assetPurchasingDetail;
	}

	public void setAssetPurchasingDetail(AssetPurchasingDTO assetPurchasingDetail) {
		this.assetPurchasingDetail = assetPurchasingDetail;
	}

	public List<AssetEventTypeAssetDTO> getAssetEventTypeAssets() {
		return assetEventTypeAssets;
	}

	public void setAssetEventTypeAssets(List<AssetEventTypeAssetDTO> assetEventTypeAssets) {
		this.assetEventTypeAssets = assetEventTypeAssets;
	}

	public List<AssetEventDTO> getAssetEvents() {
		return assetEvents;
	}

	public void setAssetEvents(List<AssetEventDTO> assetEvents) {
		this.assetEvents = assetEvents;
	}

	public List<AssetMeterReadingDTO> getAssetMeterReadings() {
		return assetMeterReadings;
	}

	public void setAssetMeterReadings(List<AssetMeterReadingDTO> assetMeterReadings) {
		this.assetMeterReadings = assetMeterReadings;
	}

	public List<AssetMeterReadingValueDTO> getAssetMeterReadingValues() {
		return assetMeterReadingValues;
	}

	public void setAssetMeterReadingValues(List<AssetMeterReadingValueDTO> assetMeterReadingValues) {
		this.assetMeterReadingValues = assetMeterReadingValues;
	}

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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

	public String getParentAssetName() {
		return parentAssetName;
	}

	public void setParentAssetName(String parentAssetName) {
		this.parentAssetName = parentAssetName;
	}

	public List<AssetFileDTO> getAssetFileDTOs() {
		return assetFileDTOs;
	}

	public void setAssetFileDTOs(List<AssetFileDTO> assetFileDTOs) {
		this.assetFileDTOs = assetFileDTOs;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public LocationDTO getLocationDTO() {
		return locationDTO;
	}

	public void setLocationDTO(LocationDTO locationDTO) {
		this.locationDTO = locationDTO;
	}

	public List<AssetDTO> getChildren() {
		return children;
	}

	public void setChildren(List<AssetDTO> children) {
		this.children = children;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	
	
}
