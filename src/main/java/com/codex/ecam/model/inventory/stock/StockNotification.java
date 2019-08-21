package com.codex.ecam.model.inventory.stock;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name="tbl_stock_notification")
public class StockNotification extends BaseModel {

	private static final long serialVersionUID = -3850310852386241291L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="stock_notification_s")
	@SequenceGenerator(name="stock_notification_s", sequenceName="stock_notification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name="stock_id")
    @ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Stock stock;

	@JoinColumn(name="user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
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
