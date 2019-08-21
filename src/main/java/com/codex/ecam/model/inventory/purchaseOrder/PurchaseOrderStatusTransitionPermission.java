package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_purchase_order_status_transition_permission")
public class PurchaseOrderStatusTransitionPermission extends BaseModel {

	private static final long serialVersionUID = 2633985352979668007L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_status_transition_permission_s")
	@SequenceGenerator(name = "purchase_order_status_transition_permission_s", sequenceName = "purchase_order_status_transition_permission_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "po_status_transition_id")
	@ManyToOne(targetEntity = PurchaseOrderStatusTransition.class, fetch = FetchType.LAZY)
	private PurchaseOrderStatusTransition statusTransition;

	@Column(name = "name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PurchaseOrderStatusTransition getStatusTransition() {
		return statusTransition;
	}

	public void setStatusTransition(PurchaseOrderStatusTransition statusTransition) {
		this.statusTransition = statusTransition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
