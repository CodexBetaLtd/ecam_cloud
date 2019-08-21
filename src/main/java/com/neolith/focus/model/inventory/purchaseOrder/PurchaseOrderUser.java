package com.neolith.focus.model.inventory.purchaseOrder;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.User;

import javax.persistence.*;

@Entity
@Table(name = "tbl_purchase_order_user")
public class PurchaseOrderUser extends BaseModel {

	private static final long serialVersionUID = 6013158558768529621L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_user_s")
	@SequenceGenerator(name = "purchase_order_user_s", sequenceName = "purchase_order_user_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder perchaseOrder;

	@JoinColumn(name = "user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PurchaseOrder getPerchaseOrder() {
		return perchaseOrder;
	}

	public void setPerchaseOrder(PurchaseOrder perchaseOrder) {
		this.perchaseOrder = perchaseOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
