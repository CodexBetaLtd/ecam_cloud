package com.codex.ecam.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
@Entity
@Table(name="tbl_country")
public class Country extends BaseModel {

	private static final long serialVersionUID = 8641823824070828671L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="country_s")
	@SequenceGenerator(name="country_s", sequenceName="country_s", allocationSize=1)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="short_name")
	private String shortName;
	

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	
}

