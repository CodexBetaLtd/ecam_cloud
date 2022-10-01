package com.codex.ecam.model.biz.part;

import javax.persistence.*;

import com.codex.ecam.listeners.inventory.part.PartNotificationLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;

@Entity
@Table(name="tbl_part_notification")
@EntityListeners(PartNotificationLogListener.class)
public class PartNotification extends BaseModel{

	private static final long serialVersionUID = -3850310852386241291L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="part_notification_s")
	@SequenceGenerator(name="part_notification_s", sequenceName="part_notification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name="part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;

	@JoinColumn(name="user_id")
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private User user;

	@Column(name="notify_on_stock_add")
	private Boolean notifyOnStockAdd;

	@Column(name="notify_on_stock_remove")
	private Boolean notifyOnStockRemove;

	@Column(name="notify_on_min_qty")
	private Boolean notifyOnMinQty; 

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

	public Asset getPart() {
		return part;
	}

	public void setPart(Asset part) {
		this.part = part;
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
