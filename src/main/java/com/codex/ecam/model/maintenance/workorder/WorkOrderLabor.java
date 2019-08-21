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
@Table(name="tbl_workorder_labor")
public class WorkOrderLabor extends BaseModel{

	private static final long serialVersionUID = -6419691131580789511L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_labor_s")
	@SequenceGenerator(name="workorder_labor_s", sequenceName="workorder_labor_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
}
