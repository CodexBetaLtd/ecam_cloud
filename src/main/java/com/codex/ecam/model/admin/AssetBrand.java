package com.codex.ecam.model.admin;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_asset_brand")
public class AssetBrand extends BaseModel{

	private static final long serialVersionUID = 5525006230906983236L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "model_asset_s")
	@SequenceGenerator(name = "model_asset_s" , sequenceName = "model_asset_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String brandName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
}