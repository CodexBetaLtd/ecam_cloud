package com.codex.ecam.model.admin;

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
@Table(name="tbl_certification")
public class Certification extends BaseModel{

	private static final long serialVersionUID = -4743569579346863040L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="certification_s")
	@SequenceGenerator(name="certification_s", sequenceName="certification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="certification_type")
	private String certificationType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
}
