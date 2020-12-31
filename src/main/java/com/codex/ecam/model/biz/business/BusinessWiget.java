package com.codex.ecam.model.biz.business;

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
import com.codex.ecam.model.app.AppWiget;

@Entity
@Table(name = "tbl_business_wiget")
public class BusinessWiget extends BaseModel {

	private static final long serialVersionUID = 6561188765583313196L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "business_wiget_s")
	@SequenceGenerator(name = "business_wiget_s", sequenceName = "business_wiget_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "business_app_id")
	@ManyToOne(targetEntity = BusinessApp.class, fetch = FetchType.LAZY)
	private BusinessApp businessApp;

	@JoinColumn(name = "app_wiget_id")
	@ManyToOne(targetEntity = AppWiget.class, fetch = FetchType.LAZY)
	private AppWiget appWiget;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

	public BusinessApp getBusinessApp() {
		return businessApp;
	}

	public void setBusinessApp(BusinessApp businessApp) {
		this.businessApp = businessApp;
	}

	public AppWiget getAppWiget() {
		return appWiget;
	}

	public void setAppWiget(AppWiget appWiget) {
		this.appWiget = appWiget;
	}




}
