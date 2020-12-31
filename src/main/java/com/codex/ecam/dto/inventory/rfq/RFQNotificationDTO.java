package com.codex.ecam.dto.inventory.rfq;

import com.codex.ecam.dto.BaseDTO;

public class RFQNotificationDTO extends BaseDTO {

	private Integer id;
	private Integer userId;
	private String userName;

	private Boolean notifyOnStatusChannged;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getNotifyOnStatusChannged() {
		return notifyOnStatusChannged;
	}

	public void setNotifyOnStatusChannged(Boolean notifyOnStatusChannged) {
		this.notifyOnStatusChannged = notifyOnStatusChannged;
	}

	

}
