package com.codex.ecam.model.asset;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.core.task.support.TaskExecutorAdapter;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.maintenance.task.Task;

@Entity
@Table(name = "tbl_asset_catogery")
public class AssetCategory extends BaseModel {

	private static final long serialVersionUID = 6632750246382394396L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_catogery_s")
	@SequenceGenerator(name = "asset_catogery_s", sequenceName = "asset_catogery_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "override_rules")
	private String overideRule;

	@Column(name = "sys_code")
	private AssetCategoryType assetCategoryType;

	@JoinColumn(name = "parent_id")
	@ManyToOne(targetEntity = AssetCategory.class, fetch = FetchType.LAZY)
	private AssetCategory parentAssetCategory;

	@JoinColumn(name = "business_id")
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@OneToMany(mappedBy = "assetCategory", fetch = FetchType.LAZY)
	private Set<Asset> assets;
	
	@OneToMany(mappedBy = "assetCategory", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,orphanRemoval=true)
	private Set<AssetCategoryTask> tasks;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOverideRule() {
		return overideRule;
	}

	public void setOverideRule(String overideRule) {
		this.overideRule = overideRule;
	}

	public AssetCategoryType getAssetCategoryType() {
		return assetCategoryType;
	}

	public void setAssetCategoryType(AssetCategoryType assetCategoryType) {
		this.assetCategoryType = assetCategoryType;
	}

	public AssetCategory getParentAssetCategory() {
		return parentAssetCategory;
	}

	public void setParentAssetCategory(AssetCategory parentAssetCategory) {
		this.parentAssetCategory = parentAssetCategory;
	}

	public Set<Asset> getAssets() {
		return assets;
	}

	public void setAssets(Set<Asset> assets) {
		updateCollection("assets", assets);
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Set<AssetCategoryTask> getTasks() {
		return tasks;
	}

	public void setTasks(Set<AssetCategoryTask> tasks) {
		updateCollection("tasks", tasks);
	}
	
	


}
