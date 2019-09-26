package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_purchase_order_change_log")
public class PurchaseOrderChangeLog extends BaseModel {

	private static final long serialVersionUID = 7575748391335655262L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_change_log_s")
	@SequenceGenerator(name = "purchase_order_change_log_s", sequenceName = "purchase_order_change_log_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder purchaseOrder;

	@Column(name = "status_id")
	private PurchaseOrderStatus status;

	@Column(name = "description")
	private String description;
	
	@Column(name = "log_type_id")
	private LogType logType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}


	public PurchaseOrderStatus getStatus() {
		return status;
	}

	public void setStatus(PurchaseOrderStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}



	
}
