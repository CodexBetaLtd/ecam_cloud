package com.codex.ecam.dto.inventory.stock;

import com.codex.ecam.dto.BaseDTO;

public class StockNotificationDTO extends BaseDTO {

	private Integer id;
	private Integer userId;
	private Integer stockId;

	private String userName;

	private Boolean notifyOnStockAdd;
	private Boolean notifyOnStockRemove;
	private Boolean notifyOnMinQty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getNotifyOnStockAdd() {
		return notifyOnStockAdd;
	}

	public void setNotifyOnStockAdd(Boolean notifyOnStockAdd) {
		this.notifyOnStockAdd = notifyOnStockAdd;
	}

	public Boolean getNotifyOnStockRemove() {
		return notifyOnStockRemove;
	}

	public void setNotifyOnStockRemove(Boolean notifyOnStockRemove) {
		this.notifyOnStockRemove = notifyOnStockRemove;
	}

	public Boolean getNotifyOnMinQty() {
		return notifyOnMinQty;
	}

	public void setNotifyOnMinQty(Boolean notifyOnMinQty) {
		this.notifyOnMinQty = notifyOnMinQty;
	}

}
