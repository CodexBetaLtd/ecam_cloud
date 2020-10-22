package com.codex.ecam.model.biz.notification;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.listeners.notification.NotificationFireListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;


@Entity
@Table(name="tbl_notification")
@EntityListeners({NotificationFireListener.class})
public class Notification extends BaseModel{

	private static final long serialVersionUID = 9104856617394907531L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="notification_s")
	@SequenceGenerator(name="notification_s", sequenceName="notification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@Column(name="subject")
	private String subject;
	
	@Column(name="content")
	private String content;
	
	@Column(name="is_open")
	private Boolean isOpen;
	
	@Column(name="is_popup")
	private Boolean isPopup;

	@Column(name="is_trashed")
	private Boolean isTrashed;

    @Column(name = "is_system_message")
    private Boolean isSystemMessage;

	@Column(name="notification_type")
	private NotificationType notificationType;
	
	@ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinColumn(name="sender_id")
	private User sender;
	
	@OneToMany(mappedBy = "notification", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<NotificationRecipientUser> recipients;


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

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Boolean getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(Boolean isPopup) {
		this.isPopup = isPopup;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Boolean getIsTrashed() {
		return isTrashed;
	}

	public void setIsTrashed(Boolean trashed) {
		isTrashed = trashed;
	}

    public Boolean getSystemMessage() {
        return isSystemMessage;
    }

    public void setSystemMessage(Boolean systemMessage) {
        isSystemMessage = systemMessage;
    }

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public Boolean getIsSystemMessage() {
		return isSystemMessage;
	}

	public void setIsSystemMessage(Boolean isSystemMessage) {
		this.isSystemMessage = isSystemMessage;
	}

	public Set<NotificationRecipientUser> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<NotificationRecipientUser> recipients) {
		updateCollection("recipients", recipients);	}
	
	
}
