package com.neolith.focus.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.listeners.asset.AssetPartLogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.inventory.bom.BOMGroupPart;

@Entity
@Table(name = "tbl_asset_consuming_ref")
@EntityListeners(AssetPartLogListener.class)
public class AssetConsumingReference extends BaseModel {

	private static final long serialVersionUID = 1466298183671353266L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_consuming_ref_s")
	@SequenceGenerator(name = "asset_consuming_ref_s", sequenceName = "asset_consuming_ref_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "bom_group_part_id")
	@ManyToOne(targetEntity = BOMGroupPart.class, fetch = FetchType.LAZY)
	private BOMGroupPart bomGroupAsset;

	@Column(name = "max_consumption")
	private Double maxConsumption;

	@PostPersist
	private void updateIdAndVersion() {
		setId(getId());
		setVersion(getVersion());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Asset getPart() {
		return part;
	}

	public void setPart(Asset part) {
		this.part = part;
	}

	public Double getMaxConsumption() {
		return maxConsumption;
	}

	public void setMaxConsumption(Double maxConsumption) {
		this.maxConsumption = maxConsumption;
	}

	public BOMGroupPart getBomGroupAsset() {
		return bomGroupAsset;
	}

	public void setBomGroupAsset(BOMGroupPart bomGroupAsset) {
		this.bomGroupAsset = bomGroupAsset;
	}
}
