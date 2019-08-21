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
@Table(name = "tbl_priority")
public class Priority extends BaseModel {

	private static final long serialVersionUID = 6982253054843115569L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "priority_s")
	@SequenceGenerator(name = "priority_s", sequenceName = "priority_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "color")
	private String color;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
