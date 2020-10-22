package com.codex.ecam.model.biz.notification;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name="tbl_notification_recipient")
public class NotificationRecipientUser extends BaseModel {

	private static final long serialVersionUID = 3995542305560456290L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "notification_recipient_s")
	@SequenceGenerator(name = "notification_recipient_s", sequenceName = "notification_recipient_s", allocationSize = 1)
	@Column(name="id")
	private Integer id;	
	
	@Column(name="is_open")
	private Boolean isOpen;
	
	@Column(name="is_popup")
	private Boolean isPopup;

	@Column(name="is_trashed")
	private Boolean isTrashed;

    @Column(name = "is_system_message")
    private Boolean isSystemMessage;
	
	@JoinColumn( name="recipient_id" )
	@ManyToOne( targetEntity = User.class,fetch = FetchType.LAZY)
	private User recipient;
	
	@JoinColumn( name="notification_id" )
	@ManyToOne( targetEntity = Notification.class, fetch = FetchType.LAZY)
	private Notification notification;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Boolean getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(Boolean isPopup) {
		this.isPopup = isPopup;
	}

	public Boolean getIsTrashed() {
		return isTrashed;
	}

	public void setIsTrashed(Boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public Boolean getIsSystemMessage() {
		return isSystemMessage;
	}

	public void setIsSystemMessage(Boolean isSystemMessage) {
		this.isSystemMessage = isSystemMessage;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
	
	
	

}
