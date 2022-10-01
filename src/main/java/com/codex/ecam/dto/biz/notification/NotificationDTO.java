package com.codex.ecam.dto.biz.notification;

import java.util.Date;

import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.dto.BaseDTO;

public class NotificationDTO extends BaseDTO {

	private Integer id;

    private Integer businessId;
    private String businessName; 

    private Integer siteId;
    private String siteName;
    
    private String sendTo;
    private String sendFrom;
	private String subject; 
	private String content; 
	
	private Boolean isOpen = Boolean.FALSE; 
	private Boolean isPopup = Boolean.FALSE;
	private Boolean isTrash = Boolean.FALSE;
    private Boolean isSystemMessage = Boolean.FALSE;

    private NotificationType notificationType;
    private Integer notificationTypeId;

	private String senderName;
	private String receiverName;

    private Integer sentUserId;
    private String sentUserName;

    private Integer receivedUserId;
	private String receivedUserName;

	private Date notificationDateTime;





	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean open) {
		isOpen = open;
	}

	public Boolean getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(Boolean popup) {
		isPopup = popup;
	}

	public Integer getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(Integer notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public Integer getReceivedUserId() {
		return receivedUserId;
	}

	public void setReceivedUserId(Integer receivedUserId) {
		this.receivedUserId = receivedUserId;
	}

	public Integer getSentUserId() {
		return sentUserId;
	}

	public void setSentUserId(Integer sentUserId) {
		this.sentUserId = sentUserId;
	}

	public Boolean getTrash() {
		return isTrash;
	}

	public void setTrash(Boolean trash) {
		isTrash = trash;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

    public Boolean getSystemMessage() {
        return isSystemMessage;
    }

    public void setSystemMessage(Boolean systemMessage) {
        isSystemMessage = systemMessage;
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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSentUserName() {
        return sentUserName;
    }

    public void setSentUserName(String sentUserName) {
        this.sentUserName = sentUserName;
    }

    public String getReceivedUserName() {
        return receivedUserName;
    }

    public void setReceivedUserName(String receivedUserName) {
        this.receivedUserName = receivedUserName;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public Boolean getIsTrash() {
		return isTrash;
	}

	public void setIsTrash(Boolean isTrash) {
		this.isTrash = isTrash;
	}

	public Boolean getIsSystemMessage() {
		return isSystemMessage;
	}

	public void setIsSystemMessage(Boolean isSystemMessage) {
		this.isSystemMessage = isSystemMessage;
	}

	public Date getNotificationDateTime() {
		return notificationDateTime;
	}

	public void setNotificationDateTime(Date notificationDateTime) {
		this.notificationDateTime = notificationDateTime;
	}
}
