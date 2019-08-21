package com.codex.ecam.dto.inventory.purchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.inventory.rfq.RFQItemDTO;
import com.codex.ecam.util.DateUtil;

public class PurchaseOrderItemDTO extends BaseDTO {

	private Integer itemId;
	private Integer itemAssetId;
	private Integer itemAccountId;
	private Integer itemChargeDepartmentId;
	private Integer itemParentId;
	private Integer itemPurchaseOrderId;
	private Integer itemRequestedByUserId;
	private Integer itemSiteId;
	private Integer itemSourceAssetId;
	private Integer itemSourceWorkOrderId;
	private Integer itemStockHistoryId;
	private Integer itemStockId;
	private Integer itemSupplierId;

	private String itemAssetName;
	private String itemSiteName;
	private String itemSourceAssetName;
	private String itemSupplierPartNo;
	private String itemCatalogName;
	private String itemSourceWorkOrderCode;
	private String itemRfqCodes;
	private String itemBusinessAssetNo;
	private String itemDescription;

	private Double itemRemoteOrgUnitPrice;
	private Double itemTaxRate = 0.0;
	private Double itemTotalPrice = 0.0;
	private Double itemUnitPrice = 0.0;
	private Integer itemQtyOnOrder = 0;

	private Date itemRequiredByDate;

	private Boolean isAddedDirectlyToPurchaseOrder;
	private Boolean isProductionEquipmentDownWhileOnOrder;
	private Boolean isSupplierConfirmed;
	
	private Integer itemRfqItemId;
	
	private List<RFQItemDTO> rfqItems = new ArrayList<>();

    private Integer purchaseOrderId;
    private String purchaseOrderCode;








	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemAssetId() {
		return itemAssetId;
	}

	public void setItemAssetId(Integer itemAssetId) {
		this.itemAssetId = itemAssetId;
	}

	public Integer getItemAccountId() {
		return itemAccountId;
	}

	public void setItemAccountId(Integer itemAccountId) {
		this.itemAccountId = itemAccountId;
	}

	public Integer getItemChargeDepartmentId() {
		return itemChargeDepartmentId;
	}

	public void setItemChargeDepartmentId(Integer itemChargeDepartmentId) {
		this.itemChargeDepartmentId = itemChargeDepartmentId;
	}

	public Integer getItemParentId() {
		return itemParentId;
	}

	public void setItemParentId(Integer itemParentId) {
		this.itemParentId = itemParentId;
	}

	public Integer getItemPurchaseOrderId() {
		return itemPurchaseOrderId;
	}

	public void setItemPurchaseOrderId(Integer itemPurchaseOrderId) {
		this.itemPurchaseOrderId = itemPurchaseOrderId;
	}

	public Integer getItemRequestedByUserId() {
		return itemRequestedByUserId;
	}

	public void setItemRequestedByUserId(Integer itemRequestedByUserId) {
		this.itemRequestedByUserId = itemRequestedByUserId;
	}

	public Integer getItemSiteId() {
		return itemSiteId;
	}

	public void setItemSiteId(Integer itemSiteId) {
		this.itemSiteId = itemSiteId;
	}

	public Integer getItemSourceAssetId() {
		return itemSourceAssetId;
	}

	public void setItemSourceAssetId(Integer itemSourceAssetId) {
		this.itemSourceAssetId = itemSourceAssetId;
	}

	public Integer getItemSourceWorkOrderId() {
		return itemSourceWorkOrderId;
	}

	public void setItemSourceWorkOrderId(Integer itemSourceWorkOrderId) {
		this.itemSourceWorkOrderId = itemSourceWorkOrderId;
	}

	public Integer getItemStockHistoryId() {
		return itemStockHistoryId;
	}

	public void setItemStockHistoryId(Integer itemStockHistoryId) {
		this.itemStockHistoryId = itemStockHistoryId;
	}

	public Integer getItemStockId() {
		return itemStockId;
	}

	public void setItemStockId(Integer itemStockId) {
		this.itemStockId = itemStockId;
	}

	public Integer getItemSupplierId() {
		return itemSupplierId;
	}

	public void setItemSupplierId(Integer itemSupplierId) {
		this.itemSupplierId = itemSupplierId;
	}

	public String getItemAssetName() {
		return itemAssetName;
	}

	public void setItemAssetName(String itemAssetName) {
		this.itemAssetName = itemAssetName;
	}

	public String getItemSiteName() {
		return itemSiteName;
	}

	public void setItemSiteName(String itemSiteName) {
		this.itemSiteName = itemSiteName;
	}

	public String getItemSourceAssetName() {
		return itemSourceAssetName;
	}

	public void setItemSourceAssetName(String itemSourceAssetName) {
		this.itemSourceAssetName = itemSourceAssetName;
	}

	public String getItemSupplierPartNo() {
		return itemSupplierPartNo;
	}

	public void setItemSupplierPartNo(String itemSupplierPartNo) {
		this.itemSupplierPartNo = itemSupplierPartNo;
	}

	public String getItemCatalogName() {
		return itemCatalogName;
	}

	public void setItemCatalogName(String itemCatalogName) {
		this.itemCatalogName = itemCatalogName;
	}

	public String getItemSourceWorkOrderCode() {
		return itemSourceWorkOrderCode;
	}

	public void setItemSourceWorkOrderCode(String itemSourceWorkOrderCode) {
		this.itemSourceWorkOrderCode = itemSourceWorkOrderCode;
	}

	public String getItemBusinessAssetNo() {
		return itemBusinessAssetNo;
	}

	public void setItemBusinessAssetNo(String itemBusinessAssetNo) {
		this.itemBusinessAssetNo = itemBusinessAssetNo;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Double getItemRemoteOrgUnitPrice() {
		return itemRemoteOrgUnitPrice;
	}

	public void setItemRemoteOrgUnitPrice(Double itemRemoteOrgUnitPrice) {
		this.itemRemoteOrgUnitPrice = itemRemoteOrgUnitPrice;
	}

	public Double getItemTaxRate() {
		return itemTaxRate;
	}

	public void setItemTaxRate(Double itemTaxRate) {
		this.itemTaxRate = itemTaxRate;
	}

	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public Double getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(Double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public Integer getItemQtyOnOrder() {
		return itemQtyOnOrder;
	}

	public void setItemQtyOnOrder(Integer itemQtyOnOrder) {
		this.itemQtyOnOrder = itemQtyOnOrder;
	}

	public String getItemRequiredByDate() {
		if (itemRequiredByDate != null) {
			return DateUtil.getSimpleDateString(itemRequiredByDate);
		}
		return "";
	}

	public void setItemRequiredByDate(Date itemRequiredByDate) {
		this.itemRequiredByDate = itemRequiredByDate;
	}

	public Boolean getIsAddedDirectlyToPurchaseOrder() {
		return isAddedDirectlyToPurchaseOrder;
	}

	public void setIsAddedDirectlyToPurchaseOrder(Boolean isAddedDirectlyToPurchaseOrder) {
		this.isAddedDirectlyToPurchaseOrder = isAddedDirectlyToPurchaseOrder;
	}

	public Boolean getIsProductionEquipmentDownWhileOnOrder() {
		return isProductionEquipmentDownWhileOnOrder;
	}

	public void setIsProductionEquipmentDownWhileOnOrder(Boolean isProductionEquipmentDownWhileOnOrder) {
		this.isProductionEquipmentDownWhileOnOrder = isProductionEquipmentDownWhileOnOrder;
	}

	public Boolean getIsSupplierConfirmed() {
		return isSupplierConfirmed;
	}

	public void setIsSupplierConfirmed(Boolean isSupplierConfirmed) {
		this.isSupplierConfirmed = isSupplierConfirmed;
	}

	public String getItemRfqCodes() {
		return itemRfqCodes;
	}

	public void setItemRfqCodes(String itemRfqCodes) {
		this.itemRfqCodes = itemRfqCodes;
	}

	public List<RFQItemDTO> getRfqItems() {
		return rfqItems;
	}

	public void setRfqItems(List<RFQItemDTO> rfqItems) {
		this.rfqItems = rfqItems;
	}

	public Integer getItemRfqItemId() {
		return itemRfqItemId;
	}

	public void setItemRfqItemId(Integer itemRfqItemId) {
		this.itemRfqItemId = itemRfqItemId;
	}

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
}
