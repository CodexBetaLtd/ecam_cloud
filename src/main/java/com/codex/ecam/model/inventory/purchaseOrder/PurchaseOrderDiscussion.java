package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_purchase_order_discussion")
public class PurchaseOrderDiscussion extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_discussion_s")
	@SequenceGenerator(name = "purchase_order_discussion_s", sequenceName = "purchase_order_discussion_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "comment")
	private String comment;

    @JoinColumn(name = "purchase_order_id")
    @ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

}
