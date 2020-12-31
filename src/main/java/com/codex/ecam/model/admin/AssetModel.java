package com.codex.ecam.model.admin;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_asset_model")
public class AssetModel extends BaseModel{

	private static final long serialVersionUID = -3754159477396736692L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="model_asset_s")
	@SequenceGenerator(name="model_asset_s", sequenceName="model_asset_s", allocationSize=1)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "name")
	private String modelName;
	
	@JoinColumn(name = "brand_id")
	@ManyToOne(targetEntity = AssetBrand.class, fetch = FetchType.LAZY)
	private AssetBrand assetBrand; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public AssetBrand getAssetBrand() {
		return assetBrand;
	}

	public void setAssetBrand(AssetBrand assetBrand) {
		this.assetBrand = assetBrand;
	}

}
