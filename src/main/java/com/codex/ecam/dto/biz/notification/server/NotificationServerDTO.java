package com.codex.ecam.dto.biz.notification.server;

import java.util.Date;

public class NotificationServerDTO {
	private Long id;
	private String content;
	private String subject;
	private String sendUser;
	private Integer userId;
	private String userName;
	private Date notifyTime;
	private NotificationServerType type;
	private Boolean isOpened=Boolean.FALSE;
	private Boolean isPopup=Boolean.FALSE;
	private Boolean isSystemMessage=Boolean.FALSE;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public NotificationServerType getType() {
		return type;
	}

	public void setType(NotificationServerType type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Boolean getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public Boolean getIsSystemMessage() {
		return isSystemMessage;
	}

	public void setIsSystemMessage(Boolean isSystemMessage) {
		this.isSystemMessage = isSystemMessage;
	}

	public Boolean getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(Boolean isPopup) {
		this.isPopup = isPopup;
	}
	
	

}
