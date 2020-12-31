package com.codex.ecam.dto.inventory.rfq;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;

public class RFQItemDTO extends BaseDTO {

	private Integer itemId;
	private Integer itemAssetId;
	private Integer itemQuotedQty;
	private Integer itemQtyRequested;
	private Integer itemPurchaseOrderItemId;

	private String itemAssetName;
	private String itemDescription;
	private String itemPurchaseOrderCodes;

	private Double itemQuotedUnitPrice;
	private Double itemQuotedTotalPrice;
	private List<PurchaseOrderDTO> purchaseOrderDTOs = new ArrayList<>();
	
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

	public String getItemAssetName() {
		return itemAssetName;
	}

	public void setItemAssetName(String itemAssetName) {
		this.itemAssetName = itemAssetName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Double getItemQuotedUnitPrice() {
		return itemQuotedUnitPrice;
	}

	public void setItemQuotedUnitPrice(Double itemQuotedUnitPrice) {
		this.itemQuotedUnitPrice = itemQuotedUnitPrice;
	}

	public Integer getItemQuotedQty() {
		return itemQuotedQty;
	}

	public void setItemQuotedQty(Integer itemQuotedQty) {
		this.itemQuotedQty = itemQuotedQty;
	}

	public Integer getItemQtyRequested() {
		return itemQtyRequested;
	}

	public void setItemQtyRequested(Integer itemQtyRequested) {
		this.itemQtyRequested = itemQtyRequested;
	}

	public Double getItemQuotedTotalPrice() {
		return itemQuotedTotalPrice;
	}

	public void setItemQuotedTotalPrice(Double itemQuotedTotalPrice) {
		this.itemQuotedTotalPrice = itemQuotedTotalPrice;
	}

	public Integer getItemPurchaseOrderItemId() {
		return itemPurchaseOrderItemId;
	}

	public void setItemPurchaseOrderItemId(Integer itemPurchaseOrderItemId) {
		this.itemPurchaseOrderItemId = itemPurchaseOrderItemId;
	}

	public String getItemPurchaseOrderCodes() {
		return itemPurchaseOrderCodes;
	}

	public void setItemPurchaseOrderCodes(String itemPurchaseOrderCodes) {
		this.itemPurchaseOrderCodes = itemPurchaseOrderCodes;
	}

	public List<PurchaseOrderDTO> getPurchaseOrderDTOs() {
		return purchaseOrderDTOs;
	}

	public void setPurchaseOrderDTOs(List<PurchaseOrderDTO> purchaseOrderDTOs) {
		this.purchaseOrderDTOs = purchaseOrderDTOs;
	}
	
	

}
