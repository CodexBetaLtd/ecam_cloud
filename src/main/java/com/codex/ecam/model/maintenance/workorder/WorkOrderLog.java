package com.codex.ecam.model.maintenance.workorder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.model.BaseModel;

@Entity
@Table( name = "tbl_wo_log" )
public class WorkOrderLog extends BaseModel {

	private static final long serialVersionUID = 6088351550183440511L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_log_s")
	@SequenceGenerator(name="workorder_log_s", sequenceName="workorder_log_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name = "work_order_id")
	@ManyToOne( targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;

	@Column(name = "log_type_id")
	private LogType logType;

	@Column(name="notes")
	private String notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
