package com.neolith.focus.model.asset;

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
@Table(name = "tbl_asset_event_type")
public class AssetEventType extends BaseModel {

	private static final long serialVersionUID = -3429652316839599225L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_event_type_s")
	@SequenceGenerator(name = "asset_event_type_s", sequenceName = "asset_event_type_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
