package com.codex.ecam.dto.maintenance.exworkorder;

import java.util.Date;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.BaseDTO;

public class ExWorkOrderDTO extends BaseDTO {

	private Integer id;
	private String code;
	private Integer businessId;
	private String businessName;
	private Integer siteId;
	private String siteName;
	private Integer assetId;
	private String assetName;
	private Integer serviceProviderId;
	private String serviceProviderName;
	private Integer serviceRequestId;
	private String serviceRequestName;
	private Date requestedDate;
	private Date completedDate;
	private Double estimatedCost;
	private Double actualCost;
	private String note;
	private String maintenanceSummary;
	private WorkOrderStatus workOrderStatus = WorkOrderStatus.OPEN;
	
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

	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getAssetId() {
		return assetId;
	}
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public Integer getServiceProviderId() {
		return serviceProviderId;
	}
	public void setServiceProviderId(Integer serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public Integer getServiceRequestId() {
		return serviceRequestId;
	}
	public void setServiceRequestId(Integer serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	public String getServiceRequestName() {
		return serviceRequestName;
	}
	public void setServiceRequestName(String serviceRequestName) {
		this.serviceRequestName = serviceRequestName;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
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
	public WorkOrderStatus getWorkOrderStatus() {
		return workOrderStatus;
	}
	public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	
}
