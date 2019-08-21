package com.neolith.focus.model.asset;

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

import com.neolith.focus.model.BaseModel;

@Entity
@Table(name = "tbl_asset_event_type_asset")
public class AssetEventTypeAsset extends BaseModel {

	private static final long serialVersionUID = -135533877203507039L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_event_type_asset_s")
	@SequenceGenerator(name = "asset_event_type_asset_s", sequenceName = "asset_event_type_asset_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_event_type_id")
	@ManyToOne(targetEntity = AssetEventType.class, fetch = FetchType.LAZY)
	private AssetEventType assetEventType;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "latest_asset_event_id")
	@ManyToOne(targetEntity = AssetEvent.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private AssetEvent latestAssetEvent;

	@OneToMany(mappedBy = "assetEventTypeAsset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetEvent> assetEvents;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssetEventType getAssetEventType() {
		return assetEventType;
	}

	public void setAssetEventType(AssetEventType assetEventType) {
		this.assetEventType = assetEventType;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Set<AssetEvent> getAssetEvents() {
		return assetEvents;
	}

	public void setAssetEvents(Set<AssetEvent> assetEvents) {
		updateCollection("assetEvents", assetEvents);
	}

	public AssetEvent getLatestAssetEvent() {
		return latestAssetEvent;
	}

	public void setLatestAssetEvent(AssetEvent latestAssetEvent) {
		this.latestAssetEvent = latestAssetEvent;
	}

}
