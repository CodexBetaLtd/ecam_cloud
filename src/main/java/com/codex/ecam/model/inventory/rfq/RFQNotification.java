package com.codex.ecam.model.inventory.rfq;

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
@Table(name="tbl_rfq_notification")
///@EntityListeners(PartNotificationLogListener.class)
public class RFQNotification extends BaseModel{

	private static final long serialVersionUID = -3850310852386241291L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="rfq_notification_s")
	@SequenceGenerator(name="rfq_notification_s", sequenceName="rfq_notification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name="rfq_id")
	@ManyToOne(targetEntity = RFQ.class, fetch = FetchType.LAZY)
	private RFQ rfq;

	@JoinColumn(name="user_id")
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private User user;

	@Column(name="notify_on_status_changed")
	private Boolean notifyOnStatusChannged;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RFQ getRfq() {
		return rfq;
	}

	public void setRfq(RFQ rfq) {
		this.rfq = rfq;
	}

	public Boolean getNotifyOnStatusChannged() {
		return notifyOnStatusChannged;
	}

	public void setNotifyOnStatusChannged(Boolean notifyOnStatusChannged) {
		this.notifyOnStatusChannged = notifyOnStatusChannged;
	}
	
	
}
