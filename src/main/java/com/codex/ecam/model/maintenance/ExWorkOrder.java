package com.codex.ecam.model.maintenance;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.supplier.Supplier;


@Entity
@Table(name="tbl_ex_workorder")
//@EntityListeners( { WorkOrderLogListener.class } )
public class ExWorkOrder extends BaseModel {

	private static final long serialVersionUID = 8003244413156374615L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ex_workorder_s")
	@SequenceGenerator(name="ex_workorder_s", sequenceName="ex_workorder_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="code")
	private String code;

	@JoinColumn( name="site_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn( name="service_provider_id" )
	@ManyToOne( targetEntity = Supplier.class, fetch = FetchType.LAZY)
	private Supplier supplier;

	@JoinColumn( name="asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn( name="requested_by_user_id" )
	@ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private User requestedByUser;

	@Column(name="note")
	private String note;

	@Column(name="maintenance_summary")
	private String maintenanceSummary;

	@Column(name="date_completed")
	@Temporal(TemporalType.DATE)
	private  Date dateCompleted;

	@Column(name = "date_requested")
	@Temporal(TemporalType.DATE)
	private Date dateRequested;


	@Column(name="estimated_cost")
	private Double estimatedCost;

	@Column(name="actual_cost")
	private Double actualCost;
	
	@Column(name="wo_status")
	private WorkOrderStatus woStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public User getRequestedByUser() {
		return requestedByUser;
	}

	public void setRequestedByUser(User requestedByUser) {
		this.requestedByUser = requestedByUser;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMaintenanceSummary() {
		return maintenanceSummary;
	}

	public void setMaintenanceSummary(String maintenanceSummary) {
		this.maintenanceSummary = maintenanceSummary;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public Date getDateRequested() {
		return dateRequested;
	}

	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}

	public Double getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(Double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public Double getActualCost() {
		return actualCost;
	}

	public void setActualCost(Double actualCost) {
		this.actualCost = actualCost;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public WorkOrderStatus getWoStatus() {
		return woStatus;
	}

	public void setWoStatus(WorkOrderStatus woStatus) {
		this.woStatus = woStatus;
	}




}
