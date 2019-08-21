package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_purchase_order_log")
public class PurchaseOrderLog extends BaseModel {

	private static final long serialVersionUID = 7575748391335655262L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_log_s")
	@SequenceGenerator(name = "purchase_order_log_s", sequenceName = "purchase_order_log_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder perchaseOrder;

	@Column(name = "from_status_id")
	private PurchaseOrderStatus fromStatus;

	@Column(name = "to_status_id")
	private PurchaseOrderStatus toStatus;

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

	public PurchaseOrderStatus getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(PurchaseOrderStatus fromStatus) {
		this.fromStatus = fromStatus;
	}

	public PurchaseOrderStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(PurchaseOrderStatus toStatus) {
		this.toStatus = toStatus;
	}

}
