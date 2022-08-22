package com.codex.ecam.dto.asset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.AssetClassType;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.inventory.AssetConsumingReferenceDTO;

public class AssetDTO extends BaseDTO {

	private Integer id;
	private Integer assetCategoryId;
	private Integer businessId;
	private Integer siteId;
	private Integer businessTypeId;
	private Integer parentAssetId;
	private Integer countryId;
	private Integer model;
	private Integer customerId;
	private Integer currentAssetEventId;
	private Integer childCount;
	private Integer brand;

	private String name;
	private String code;
	private String description;
	private String imageLocation;
	private String businessName;
	private String notes;
	private String assetCategoryName;
	private String parentAssetName;
	private String countryName;

	private AssetCategoryType assetCategoryType;

	private Boolean isOnline = true;

	// location
	private String location;
	private String address;
	private String city;
	private String province;
	private String postalCode;
	private String brandName;
	private String modelName;
	private String serialNo;
	private String customerName;
	private String assetUrl;
	
	private String department;
	private AssetClassType assetClass;
	
	private BigDecimal size;
	private BigDecimal quantity;
	private BigDecimal unitCost;
	private BigDecimal totalCost;
	private BigDecimal usefulLife;
	private BigDecimal yearlyDepreciationValue;
	private BigDecimal yearEndNetBookValue ;
	private BigDecimal accumulatedDepreciation ;
	
	private Date dateOfPurchase;

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
	private List<SparePartDTO> sparePartDTOs = new ArrayList<>();

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

	public List<SparePartDTO> getSparePartDTOs() {
		return sparePartDTOs;
	}

	public void setSparePartDTOs(List<SparePartDTO> sparePartDTOs) {
		this.sparePartDTOs = sparePartDTOs;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public AssetClassType getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(AssetClassType assetClass) {
		this.assetClass = assetClass;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getUsefulLife() {
		return usefulLife;
	}

	public void setUsefulLife(BigDecimal usefulLife) {
		this.usefulLife = usefulLife;
	}

	public BigDecimal getYearlyDepreciationValue() {
		return yearlyDepreciationValue;
	}

	public void setYearlyDepreciationValue(BigDecimal yearlyDepreciationValue) {
		this.yearlyDepreciationValue = yearlyDepreciationValue;
	}

	public BigDecimal getYearEndNetBookValue() {
		return yearEndNetBookValue;
	}

	public void setYearEndNetBookValue(BigDecimal yearEndNetBookValue) {
		this.yearEndNetBookValue = yearEndNetBookValue;
	}

	public BigDecimal getAccumulatedDepreciation() {
		return accumulatedDepreciation;
	}

	public void setAccumulatedDepreciation(BigDecimal accumulatedDepreciation) {
		this.accumulatedDepreciation = accumulatedDepreciation;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}



}
