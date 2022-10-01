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
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_asset_brand")
public class AssetBrand extends BaseModel{

	private static final long serialVersionUID = 5525006230906983236L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_brand_s")
	@SequenceGenerator(name = "asset_brand_s" , sequenceName = "asset_brand_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String brandName;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	public AssetBrand() { }

	public AssetBrand(String brandName, Business business, Boolean isDeleted) {

		setBrandName(brandName);
		setBusiness(business);
		setIsDeleted(isDeleted);

	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}