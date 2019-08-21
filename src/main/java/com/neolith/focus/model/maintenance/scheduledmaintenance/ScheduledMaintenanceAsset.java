package com.neolith.focus.model.maintenance.scheduledmaintenance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.listeners.scheduledmaintenance.ScheduledMaintenanceAssetLogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;

@Entity
@Table(name = "tbl_scheduled_maintenance_asset")
@EntityListeners( ScheduledMaintenanceAssetLogListener.class )
public class ScheduledMaintenanceAsset extends BaseModel {

	private static final long serialVersionUID = 972208513274075010L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_maintenance_asset_s")
	@SequenceGenerator(name = "scheduled_maintenance_asset_s", sequenceName = "scheduled_maintenance_asset_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "scheduled_maintenance_id")
	@ManyToOne(targetEntity = ScheduledMaintenance.class, fetch = FetchType.LAZY)
	private ScheduledMaintenance scheduledMaintenance;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

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

	public ScheduledMaintenance getScheduledMaintenance() {
		return scheduledMaintenance;
	}

	public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
		this.scheduledMaintenance = scheduledMaintenance;
	}
}
