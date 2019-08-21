package com.codex.ecam.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_asset_status")
public class AssetStatus extends BaseModel{

	private static final long serialVersionUID = 2623861372144625491L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_status_s")
	@SequenceGenerator(name="asset_status_s", sequenceName="asset_status_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
