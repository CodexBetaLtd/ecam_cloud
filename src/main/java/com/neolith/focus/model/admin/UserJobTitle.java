package com.neolith.focus.model.admin;

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

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.biz.business.Business;
@Entity
@Table(name="tbl_user_job_title")
public class UserJobTitle extends BaseModel {

	private static final long serialVersionUID = 8641823824070828671L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_job_title_s")
	@SequenceGenerator(name="user_job_title_s", sequenceName="user_job_title_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="job_title_name")
	private String jobTitle;

	@Column(name="description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
}
