package com.codex.ecam.dto.inventory.receiptOrder;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class ReceiptOrderItemDTO extends BaseDTO {

	private Integer itemId;
	private Integer itemAssetId;
	private Integer itemStockId;
	private String itemStockName;
	private String itemAssetName;
	private String itemDescription;
	private BigDecimal itemUnitPrice;
	private BigDecimal itemQtyReceived;
	private Integer issueNoteitemId;



	/*===================================*/

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

	public Integer getItemStockId() {
		return itemStockId;
	}

	public void setItemStockId(Integer itemStockId) {
		this.itemStockId = itemStockId;
	}

	public String getItemStockName() {
		return itemStockName;
	}

	public void setItemStockName(String itemStockName) {
		this.itemStockName = itemStockName;
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

	public BigDecimal getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public BigDecimal getItemQtyReceived() {
		return itemQtyReceived;
	}

	public void setItemQtyReceived(BigDecimal itemQtyReceived) {
		this.itemQtyReceived = itemQtyReceived;
	}

	public Integer getIssueNoteitemId() {
		return issueNoteitemId;
	}

	public void setIssueNoteitemId(Integer issueNoteitemId) {
		this.issueNoteitemId = issueNoteitemId;
	}
	
	
}
