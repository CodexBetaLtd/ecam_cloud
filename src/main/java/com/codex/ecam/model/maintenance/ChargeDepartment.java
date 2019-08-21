package com.codex.ecam.model.maintenance;

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

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;
@Entity
@Table(name="tbl_charge_department")
public class ChargeDepartment extends BaseModel{

	private static final long serialVersionUID = -3429652316839599225L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="tbl_charge_department_s")
	@SequenceGenerator(name="tbl_charge_department_s", sequenceName="tbl_charge_department_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="code")
	private String code;

	@Column(name="description")
	private String description;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
}
