package com.codex.ecam.model.maintenance.workorder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_workorder_cost")
public class WorkOrderCost extends BaseModel {

	private static final long serialVersionUID = 723201845274075085L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_cost_s")
	@SequenceGenerator(name="workorder_cost_s", sequenceName="workorder_cost_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}

}
