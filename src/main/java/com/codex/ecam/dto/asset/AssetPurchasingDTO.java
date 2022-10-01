package com.codex.ecam.dto.asset;

import java.util.Date;

public class AssetPurchasingDTO {

	private Integer receiptOrderId;
	private Integer purchasedSupplierId;
	private Integer purchasedCurrencyId;
	private Integer itemVersion;
	private Integer orderVersion;

	private Date orderedDate;
	private Date receivedDate;
	private Date expiryDate;
	private Double purchasedPrice;

	private String receiptOrderCode;
	private String purchasedSupplierName;
	private String purchasedCurrencyName;

	public Integer getReceiptOrderId() {
		return receiptOrderId;
	}

	public void setReceiptOrderId(Integer receiptOrderId) {
		this.receiptOrderId = receiptOrderId;
	}

	public String getReceiptOrderCode() {
		return receiptOrderCode;
	}

	public void setReceiptOrderCode(String receiptOrderCode) {
		this.receiptOrderCode = receiptOrderCode;
	}

	public Integer getPurchasedSupplierId() {
		return purchasedSupplierId;
	}

	public void setPurchasedSupplierId(Integer purchasedSupplierId) {
		this.purchasedSupplierId = purchasedSupplierId;
	}

	public Integer getPurchasedCurrencyId() {
		return purchasedCurrencyId;
	}

	public void setPurchasedCurrencyId(Integer purchasedCurrencyId) {
		this.purchasedCurrencyId = purchasedCurrencyId;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getPurchasedPrice() {
		return purchasedPrice;
	}

	public void setPurchasedPrice(Double purchasedPrice) {
		this.purchasedPrice = purchasedPrice;
	}

	public Integer getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Integer itemVersion) {
		this.itemVersion = itemVersion;
	}

	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	public String getPurchasedSupplierName() {
		return purchasedSupplierName;
	}

	public void setPurchasedSupplierName(String purchasedSupplierName) {
		this.purchasedSupplierName = purchasedSupplierName;
	}

	public String getPurchasedCurrencyName() {
		return purchasedCurrencyName;
	}

	public void setPurchasedCurrencyName(String purchasedCurrencyName) {
		this.purchasedCurrencyName = purchasedCurrencyName;
	}

}
