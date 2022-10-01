package com.codex.ecam.model.maintenance.workorder;

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

import com.codex.ecam.model.BaseModel;
@Entity
@Table(name="tbl_wo_request_task")
public class WorkOrderRequestTask extends BaseModel {

	private static final long serialVersionUID = 2028252240695599758L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_request_task_s")
	@SequenceGenerator(name="workorder_request_task_s", sequenceName="workorder_request_task_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	/*TODO
	 * 	fk_workorder_request_id
	 * 	fk_maintenance_task_id
		fk_task_type_id
	 */

	@Column(name="from_date")
	@Temporal(TemporalType.DATE)
	private  Date  fromDate;

	@Column(name="to_date")
	@Temporal(TemporalType.DATE)
	private  Date  toDate;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}



}
