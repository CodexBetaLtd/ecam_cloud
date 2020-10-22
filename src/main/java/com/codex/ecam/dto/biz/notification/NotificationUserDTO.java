package com.codex.ecam.dto.biz.notification;

import com.codex.ecam.dto.BaseDTO;

public class NotificationUserDTO extends BaseDTO {

	private Integer id;

    private Integer businessId;
    private String businessName; 

    private Integer receivedUserId;
	private String receivedUserName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getReceivedUserId() {
		return receivedUserId;
	}
	public void setReceivedUserId(Integer receivedUserId) {
		this.receivedUserId = receivedUserId;
	}
	public String getReceivedUserName() {
		return receivedUserName;
	}
	public void setReceivedUserName(String receivedUserName) {
		this.receivedUserName = receivedUserName;
	}



}
