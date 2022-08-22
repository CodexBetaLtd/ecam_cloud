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

	public AssetModel(){}

	public AssetModel(String modelName, AssetBrand brand, Boolean isDeleted) {
		setModelName(modelName);
		setAssetBrand(brand);
		setIsDeleted(false);
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
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
