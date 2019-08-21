package com.neolith.focus.model.maintenance.workorder;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.neolith.focus.model.BaseModel;

@Entity
@Table(name="tbl_wo_request")
public class WorkOrderRequest extends BaseModel{

	private static final long serialVersionUID = 8738014110256189154L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_request_s")
	@SequenceGenerator(name="workorder_request_s", sequenceName="workorder_request_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="workorder_request_no")
	private String workorderRequestNo;

	@Column(name="workorder_requested_date")
	@Temporal(TemporalType.DATE)
	private  Date  workorderRequestedDate;

	@Column(name="workOrder_approved_date")
	@Temporal(TemporalType.DATE)
	private  Date  workOrderApprovedDate;

	@Column(name="workOrder_requested_from_date")
	@Temporal(TemporalType.DATE)
	private  Date  workOrderRequestedFromDate;

	@Column(name="workOrder_requested_to_date")
	@Temporal(TemporalType.DATE)
	private  Date  workOrderRequestedToDate;

	@Column(name="is_revised")
	private Boolean isRevised;

	@Column(name="revisedDate")
	@Temporal(TemporalType.DATE)
	private  Date  revised_date;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}



}
