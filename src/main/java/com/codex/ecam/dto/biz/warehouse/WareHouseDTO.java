package com.codex.ecam.dto.biz.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.LocationDTO;

public class WareHouseDTO extends BaseDTO {

	private Integer id;
	private Integer assetCategoryId;
	private Integer parentAssetCategoryId;
	private Integer parentAssetId;
	private Integer businessId;
	private Integer siteId;
	private Integer countryId;

	private AssetCategoryType assetCategoryType;

	private String assetCategoryName;
	private String parentAssetCategoryName;
	private String name;
	private String code;
	private String description;
	private String parentAssetName;
	private String location;
	private String businessName;
	private String siteName;

	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String address;
	private String city;
	private String province;
	private String postalCode;
	private String countryName;

	private List<AssetDTO> children = new ArrayList<>();

	private Integer childCount;

	private BigDecimal itemQty = new BigDecimal(50);

	private Boolean isDefault=Boolean.FALSE;

	private LocationDTO locationDTO = new LocationDTO();


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AssetCategoryType getAssetCategoryType() {
		return assetCategoryType;
	}
	public void setAssetCategoryType(AssetCategoryType assetCategoryType) {
		this.assetCategoryType = assetCategoryType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
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
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public BigDecimal getItemQty() {
		return itemQty;
	}

	public void setItemQty(BigDecimal itemQty) {
		this.itemQty = itemQty;
	}
	public Integer getAssetCategoryId() {
		return assetCategoryId;
	}
	public void setAssetCategoryId(Integer assetCategoryId) {
		this.assetCategoryId = assetCategoryId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getParentAssetCategoryId() {
		return parentAssetCategoryId;
	}
	public void setParentAssetCategoryId(Integer parentAssetCategoryId) {
		this.parentAssetCategoryId = parentAssetCategoryId;
	}
	public String getParentAssetCategoryName() {
		return parentAssetCategoryName;
	}
	public void setParentAssetCategoryName(String parentAssetCategoryName) {
		this.parentAssetCategoryName = parentAssetCategoryName;
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
	public String getParentAssetName() {
		return parentAssetName;
	}
	public void setParentAssetName(String parentAssetName) {
		this.parentAssetName = parentAssetName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}





}
