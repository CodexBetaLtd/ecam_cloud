package com.codex.ecam.dto.biz.part;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.constants.inventory.PartUsageType;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.asset.AssetUserDTO;
import com.codex.ecam.dto.asset.WarrantyDTO;
import com.codex.ecam.dto.inventory.AssetConsumingReferenceDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderItemDTO;
import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stock.StockLedgerDTO;

public class PartDTO extends BaseDTO {

	private Integer businessId;
	private String businessName;
	private Integer siteId;
	private String siteName;
	private String imageLocation;

	private Integer id;
	private String name;
	private String code;
	private String description;
	private Integer businessGroupId;
	private Integer accountId;
	private Integer chargeDepartmentId;
	private String inventoryCode;
	private String unspcCode;
	private String barcode;
	private String make;
	private Integer brandId;
	private String brandName;
	private String model;
	private Integer modelId;
	private String modelName;
	private Integer lastPrice;
	private String completionNotes;
	private Integer partCategoryId;
	private String partCategoryName;
	private PartType partType=PartType.NORMAL;
	private PartUsageType partUsageType=PartUsageType.NORMAL_PART;
	

	private List<StockDTO> stockDTOs = new ArrayList<>();
	private List<AssetUserDTO> assetUserDTOs = new ArrayList<>();
	private List<AssetConsumingReferenceDTO> assetConsumeRefs = new ArrayList<>();
	private List<WarrantyDTO> warranties = new ArrayList<>();
	private List<PurchaseOrderItemDTO> openPOs = new ArrayList<>();
	private List<PartNotificationDTO> partNotificationDTOs = new ArrayList<>();
    private List<StockLedgerDTO> ledgerDTOs = new ArrayList<>();

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

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getChargeDepartmentId() {
		return chargeDepartmentId;
	}

	public void setChargeDepartmentId(Integer chargeDepartmentId) {
		this.chargeDepartmentId = chargeDepartmentId;
	}

	public String getInventoryCode() {
		return inventoryCode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	public String getUnspcCode() {
		return unspcCode;
	}

	public void setUnspcCode(String unspcCode) {
		this.unspcCode = unspcCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Integer lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getCompletionNotes() {
		return completionNotes;
	}

	public void setCompletionNotes(String completionNotes) {
		this.completionNotes = completionNotes;
	}

	public List<AssetUserDTO> getAssetUserDTOs() {
		return assetUserDTOs;
	}

	public void setAssetUserDTOs(List<AssetUserDTO> assetUserDTOs) {
		this.assetUserDTOs = assetUserDTOs;
	}

	public List<StockDTO> getStockDTOs() {
		return stockDTOs;
	}

	public void setStockDTOs(List<StockDTO> stockDTOs) {
		this.stockDTOs = stockDTOs;
	}

	public List<AssetConsumingReferenceDTO> getAssetConsumeRefs() {
		return assetConsumeRefs;
	}

	public void setAssetConsumeRefs(List<AssetConsumingReferenceDTO> assetConsumeRefs) {
		this.assetConsumeRefs = assetConsumeRefs;
	}

	public List<WarrantyDTO> getWarranties() {
		return warranties;
	}

	public void setWarranties(List<WarrantyDTO> warranties) {
		this.warranties = warranties;
	}

	public List<PurchaseOrderItemDTO> getOpenPOs() {
		return openPOs;
	}

	public void setOpenPOs(List<PurchaseOrderItemDTO> openPOs) {
		this.openPOs = openPOs;
	}

	public Integer getBusinessGroupId() {
		return businessGroupId;
	}

	public void setBusinessGroupId(Integer businessGroupId) {
		this.businessGroupId = businessGroupId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public Integer getPartCategoryId() {
		return partCategoryId;
	}

	public void setPartCategoryId(Integer partCategoryId) {
		this.partCategoryId = partCategoryId;
	}

	public String getPartCategoryName() {
		return partCategoryName;
	}

	public void setPartCategoryName(String partCategoryName) {
		this.partCategoryName = partCategoryName;
	}

	public List<PartNotificationDTO> getPartNotificationDTOs() {
		return partNotificationDTOs;
	}

	public void setPartNotificationDTOs(List<PartNotificationDTO> partNotificationDTOs) {
		this.partNotificationDTOs = partNotificationDTOs;
	}

	public List<StockLedgerDTO> getLedgerDTOs() {
		return ledgerDTOs;
	}

	public void setLedgerDTOs(List<StockLedgerDTO> ledgerDTOs) {
		this.ledgerDTOs = ledgerDTOs;
	}

	public PartType getPartType() {
		return partType;
	}

	public void setPartType(PartType partType) {
		this.partType = partType;
	}

	public PartUsageType getPartUsageType() {
		return partUsageType;
	}

	public void setPartUsageType(PartUsageType partUsageType) {
		this.partUsageType = partUsageType;
	}


	 
	
}
