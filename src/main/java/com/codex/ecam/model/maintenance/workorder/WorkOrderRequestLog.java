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
@Table(name="wo_request_log")
public class WorkOrderRequestLog extends BaseModel{

	private static final long serialVersionUID = 4869210211494401226L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_equestLog_s")
	@SequenceGenerator(name="workorder_equestLog_s", sequenceName="workorder_equestLog_s", allocationSize=1)
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
