package com.codex.ecam.model.inventory.receiptOrder;

import javax.persistence.*;

import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_receipt_order_status_transition")
public class ReceiptOrderStatusTransition extends BaseModel {

	private static final long serialVersionUID = -3720422898735649264L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "receipt_order_status_transition_s")
	@SequenceGenerator(name = "receipt_order_status_transition_s", sequenceName = "receipt_order_status_transition_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "from_status_id")
	private ReceiptOrderStatus fromStatus;

	@Column(name = "to_status_id")
	private ReceiptOrderStatus toStatus;

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

	public ReceiptOrderStatus getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(ReceiptOrderStatus fromStatus) {
		this.fromStatus = fromStatus;
	}

	public ReceiptOrderStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(ReceiptOrderStatus toStatus) {
		this.toStatus = toStatus;
	}

	public Boolean getIsCommentRequired() {
		return isCommentRequired;
	}

	public void setIsCommentRequired(Boolean isCommentRequired) {
		this.isCommentRequired = isCommentRequired;
	}

}
