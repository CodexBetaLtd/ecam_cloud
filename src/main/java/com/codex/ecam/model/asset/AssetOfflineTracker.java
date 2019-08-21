package com.codex.ecam.model.asset;

import java.util.Date;

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
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

@Entity
@Table(name="tbl_asset_offline_tracker")
public class AssetOfflineTracker extends BaseModel {
	
	private static final long serialVersionUID = -6843265275244584564L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_offline_tracker_s")
	@SequenceGenerator(name="asset_offline_tracker_s", sequenceName="asset_offline_tracker_s", allocationSize=1)
	@Column(name="id")
	private Integer id;
	
	@JoinColumn( name="work_order_id" )
	@ManyToOne( targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;
	
	@JoinColumn( name="asset_event_type" )
	@ManyToOne( targetEntity = AssetEventType.class, fetch = FetchType.LAZY)
	private AssetEventType assetEventType;
	
	@JoinColumn( name="asset_is_located_at_asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset assetIsLocatedAtAsset;
	
	@JoinColumn( name="asset_is_part_of_asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset assetIsPartOfAsset;
	
	@Column(name="reason_offline_id")
	private Integer reasonOfflineId;
	
	@Column(name="reason_online_id")
	private Integer reasonOnlineId;
	
	@Column(name="send_to_facility_id")
	private Integer sendToFacilityId;
	
	@JoinColumn( name="set_offline_by_user_id" )
	@ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private User setOfflineByUser;
	
	@JoinColumn( name="set_online_by_user_id" )
	@ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private User setOnlineByUser;
	
	@JoinColumn( name="status_changed_by_user_id" )
	@ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private Integer statusChangedByUser;
	
	@JoinColumn( name="swap_with_asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset swapWithAsset;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="offline_info")
	private String offlineInfo;
	
	@Column(name="online_info")
	private String onlineInfo;
	
	@Column(name="affeted_hours")
	private Double affectedHours;
	
	@Column(name="offline_from_date")
	private Date offlineFromDate;
	
	@Column(name="offline_to_date")
	private Date offlineToDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public AssetEventType getAssetEventType() {
		return assetEventType;
	}

	public void setAssetEventType(AssetEventType assetEventType) {
		this.assetEventType = assetEventType;
	}

	public Asset getAssetIsLocatedAtAsset() {
		return assetIsLocatedAtAsset;
	}

	public void setAssetIsLocatedAtAsset(Asset assetIsLocatedAtAsset) {
		this.assetIsLocatedAtAsset = assetIsLocatedAtAsset;
	}

	public Asset getAssetIsPartOfAsset() {
		return assetIsPartOfAsset;
	}

	public void setAssetIsPartOfAsset(Asset assetIsPartOfAsset) {
		this.assetIsPartOfAsset = assetIsPartOfAsset;
	}

	public Integer getReasonOfflineId() {
		return reasonOfflineId;
	}

	public void setReasonOfflineId(Integer reasonOfflineId) {
		this.reasonOfflineId = reasonOfflineId;
	}

	public Integer getReasonOnlineId() {
		return reasonOnlineId;
	}

	public void setReasonOnlineId(Integer reasonOnlineId) {
		this.reasonOnlineId = reasonOnlineId;
	}

	public Integer getSendToFacilityId() {
		return sendToFacilityId;
	}

	public void setSendToFacilityId(Integer sendToFacilityId) {
		this.sendToFacilityId = sendToFacilityId;
	}

	public User getSetOfflineByUser() {
		return setOfflineByUser;
	}

	public void setSetOfflineByUser(User setOfflineByUser) {
		this.setOfflineByUser = setOfflineByUser;
	}

	public User getSetOnlineByUser() {
		return setOnlineByUser;
	}

	public void setSetOnlineByUser(User setOnlineByUser) {
		this.setOnlineByUser = setOnlineByUser;
	}

	public Integer getStatusChangedByUser() {
		return statusChangedByUser;
	}

	public void setStatusChangedByUser(Integer statusChangedByUser) {
		this.statusChangedByUser = statusChangedByUser;
	}

	public Asset getSwapWithAsset() {
		return swapWithAsset;
	}

	public void setSwapWithAsset(Asset swapWithAsset) {
		this.swapWithAsset = swapWithAsset;
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

	public String getOfflineInfo() {
		return offlineInfo;
	}

	public void setOfflineInfo(String offlineInfo) {
		this.offlineInfo = offlineInfo;
	}

	public String getOnlineInfo() {
		return onlineInfo;
	}

	public void setOnlineInfo(String onlineInfo) {
		this.onlineInfo = onlineInfo;
	}

	public Double getAffectedHours() {
		return affectedHours;
	}

	public void setAffectedHours(Double affectedHours) {
		this.affectedHours = affectedHours;
	}

	public Date getOfflineFromDate() {
		return offlineFromDate;
	}

	public void setOfflineFromDate(Date offlineFromDate) {
		this.offlineFromDate = offlineFromDate;
	}

	public Date getOfflineToDate() {
		return offlineToDate;
	}

	public void setOfflineToDate(Date offlineToDate) {
		this.offlineToDate = offlineToDate;
	}
	
}
