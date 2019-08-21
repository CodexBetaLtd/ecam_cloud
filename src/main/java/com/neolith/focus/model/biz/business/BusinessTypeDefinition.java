package com.neolith.focus.model.biz.business;

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

@Entity
@Table(name="tbl_business_type_def")
public class BusinessTypeDefinition extends BaseModel{

	private static final long serialVersionUID = -3664629778783172527L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="business_type_def_s")
	@SequenceGenerator(name="business_type_def_s", sequenceName="business_type_def_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="default_parent_id")
	private Boolean defaultParentId;

	@Column(name="definable")
	private Boolean definable;

	@Column(name="all_parent")
	private String allParent;

	@Column(name="business_type_def_name")
	private String businessTypeDefinitionName;

	@Column(name="business_type_def_short")
	private String businessTypeDefinitionShort;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getDefaultParentId() {
		return defaultParentId;
	}

	public void setDefaultParentId(Boolean defaultParentId) {
		this.defaultParentId = defaultParentId;
	}

	public Boolean getDefinable() {
		return definable;
	}

	public void setDefinable(Boolean definable) {
		this.definable = definable;
	}

	public String getAllParent() {
		return allParent;
	}

	public void setAllParent(String allParent) {
		this.allParent = allParent;
	}

	public String getBusinessTypeDefinitionName() {
		return businessTypeDefinitionName;
	}

	public void setBusinessTypeDefinitionName(String businessTypeDefinitionName) {
		this.businessTypeDefinitionName = businessTypeDefinitionName;
	}

	public String getBusinessTypeDefinitionShort() {
		return businessTypeDefinitionShort;
	}

	public void setBusinessTypeDefinitionShort(String businessTypeDefinitionShort) {
		this.businessTypeDefinitionShort = businessTypeDefinitionShort;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
