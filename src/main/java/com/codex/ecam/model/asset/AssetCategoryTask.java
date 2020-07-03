package com.codex.ecam.model.asset;

import javax.persistence.CascadeType;
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
import com.codex.ecam.model.maintenance.task.Task;

@Entity
@Table(name="tbl_asset_category_task")
public class AssetCategoryTask extends BaseModel {

	private static final long serialVersionUID = -4742026916418181279L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_category_task_s")
	@SequenceGenerator(name="asset_category_task_s", sequenceName="asset_category_task_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="asset_category_id" )
	@ManyToOne( targetEntity = AssetCategory.class, fetch = FetchType.LAZY, cascade = { CascadeType.ALL } )
	private AssetCategory assetCategory;

	@JoinColumn( name="task_id" )
	@ManyToOne( targetEntity = Task.class, fetch = FetchType.LAZY, cascade ={ CascadeType.ALL} )
	private Task task;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssetCategory getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
}
