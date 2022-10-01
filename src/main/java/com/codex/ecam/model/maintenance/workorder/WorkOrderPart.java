package com.codex.ecam.model.maintenance.workorder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.listeners.workorder.WorkOrderPartLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.inventory.stock.Stock;

@Entity
@Table(name="tbl_wo_parts")
@EntityListeners( WorkOrderPartLogListener.class )
public class WorkOrderPart extends BaseModel{

	private static final long serialVersionUID = 1054771837683166918L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_parts_s")
	@SequenceGenerator(name="workorder_parts_s", sequenceName="workorder_parts_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name = "work_order_task_id")
	@ManyToOne(targetEntity = WorkOrderTask.class, fetch = FetchType.LAZY)
	private WorkOrderTask workOrderTask;

	@JoinColumn(name = "part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;

	@JoinColumn(name = "work_order_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	@JoinColumn(name = "stock_id")
	@ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;

	@Column(name="estimated_quantity")
	private Double estimatedQuantity;

	@Column(name="actual_quantity")
	private Double actualQuantity;

	@Column(name="remark")
	private String 	remark;


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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Double getEstimatedQuantity() {
		return estimatedQuantity;
	}

	public void setEstimatedQuantity(Double estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}

	public Double getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public WorkOrderTask getWorkOrderTask() {
		return workOrderTask;
	}

	public void setWorkOrderTask(WorkOrderTask workOrderTask) {
		this.workOrderTask = workOrderTask;
	}

}
