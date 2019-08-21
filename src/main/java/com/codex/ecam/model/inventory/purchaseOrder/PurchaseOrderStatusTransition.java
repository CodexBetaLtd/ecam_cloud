package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_purchase_order_status_transition")
public class PurchaseOrderStatusTransition extends BaseModel {

	private static final long serialVersionUID = 2546921999490593335L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_status_transition_s")
	@SequenceGenerator(name = "purchase_order_status_transition_s", sequenceName = "purchase_order_status_transition_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "from_status_id")
	private PurchaseOrderStatus fromStatus;

	@Column(name = "to_status_id")
	private PurchaseOrderStatus toStatus;

	@Column(name = "is_comment_required")
	private Boolean isCommentRequired;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Boolean getIsCommentRequired() {
		return isCommentRequired;
	}

	public void setIsCommentRequired(Boolean isCommentRequired) {
		this.isCommentRequired = isCommentRequired;
	}

}
