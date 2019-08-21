package com.neolith.focus.dto.maintenance.scheduledmaintenance;

import java.util.ArrayList;
import java.util.List;

import com.neolith.focus.constants.WorkOrderStatus;
import com.neolith.focus.dto.BaseDTO;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.dto.inventory.stock.StockDTO;

public class ScheduledMaintenanceDTO extends BaseDTO {

	private Integer id;

	private Integer businessId;
	private Integer siteId;
	private Integer projectId;
	private Integer priorityId;
	private Integer maintenanceTypeId;
	private Integer accountId;
	private Integer chargeDepartmentId;
	private Integer requestorId;

	private String scheduledDate;

	private String code;
	private String businessName;
	private String assetName;
	private String assetNameStr;
	private String description;
	private String adminNotes;
	private String completionNotes;
	private String rType;
	private String scheduleDescription;
	private String requestorName;
	private String projectName;

	private Boolean notifyTechniciansWhenDWOE;
	private Boolean notifyCreatorWhenDWOE;

	private Double suggestedTime;
	private Double timeEstimatedHours;

	private Boolean isRunning  = false;
	private Integer suggestedCompletion;

	private WorkOrderStatus workOrderStatus;

	private List<ScheduledMaintenanceTriggerDTO> triggers = new ArrayList<>();
	private List<ScheduledMaintenanceNotificationDTO> notifications = new ArrayList<>();
	private List<ScheduledMaintenanceTaskDTO> scheduledTasks = new ArrayList<>();
	private List<ScheduledMaintenancePartDTO> parts = new ArrayList<>();
	private List<ScheduledMaintenanceFileDTO> files = new ArrayList<>();

	private List<AssetDTO> assets = new ArrayList<>();
	private List<StockDTO> stocks = new ArrayList<>();


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}

	public Integer getMaintenanceTypeId() {
		return maintenanceTypeId;
	}

	public void setMaintenanceTypeId(Integer maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getChargeDepartmentId() {
		return chargeDepartmentId;
	}

	public void setChargeDepartmentId(Integer chargeDepartmentId) {
		this.chargeDepartmentId = chargeDepartmentId;
	}

	public Integer getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(Integer requestorId) {
		this.requestorId = requestorId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAssetNameStr() {
		return assetNameStr;
	}

	public void setAssetNameStr(String assetNameStr) {
		this.assetNameStr = assetNameStr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdminNotes() {
		return adminNotes;
	}

	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}

	public String getCompletionNotes() {
		return completionNotes;
	}

	public void setCompletionNotes(String completionNotes) {
		this.completionNotes = completionNotes;
	}

	public String getrType() {
		return rType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}

	public String getScheduleDescription() {
		return scheduleDescription;
	}

	public void setScheduleDescription(String scheduleDescription) {
		this.scheduleDescription = scheduleDescription;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Boolean getNotifyTechniciansWhenDWOE() {
		return notifyTechniciansWhenDWOE;
	}

	public void setNotifyTechniciansWhenDWOE(Boolean notifyTechniciansWhenDWOE) {
		this.notifyTechniciansWhenDWOE = notifyTechniciansWhenDWOE;
	}

	public Boolean getNotifyCreatorWhenDWOE() {
		return notifyCreatorWhenDWOE;
	}

	public void setNotifyCreatorWhenDWOE(Boolean notifyCreatorWhenDWOE) {
		this.notifyCreatorWhenDWOE = notifyCreatorWhenDWOE;
	}

	public Double getSuggestedTime() {
		return suggestedTime;
	}

	public void setSuggestedTime(Double suggestedTime) {
		this.suggestedTime = suggestedTime;
	}

	public Double getTimeEstimatedHours() {
		return timeEstimatedHours;
	}

	public void setTimeEstimatedHours(Double timeEstimatedHours) {
		this.timeEstimatedHours = timeEstimatedHours;
	}

	public Integer getSuggestedCompletion() {
		return suggestedCompletion;
	}

	public void setSuggestedCompletion(Integer suggestedCompletion) {
		this.suggestedCompletion = suggestedCompletion;
	}

	public WorkOrderStatus getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public List<AssetDTO> getAssets() {
		return assets;
	}

	public void setAssets(List<AssetDTO> assets) {
		this.assets = assets;
	}

	public List<ScheduledMaintenanceTriggerDTO> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<ScheduledMaintenanceTriggerDTO> triggers) {
		this.triggers = triggers;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public List<StockDTO> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockDTO> stocks) {
		this.stocks = stocks;
	}

	public List<ScheduledMaintenanceNotificationDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<ScheduledMaintenanceNotificationDTO> notifications) {
		this.notifications = notifications;
	}

	public List<ScheduledMaintenanceTaskDTO> getScheduledTasks() {
		return scheduledTasks;
	}

	public void setScheduledTasks(List<ScheduledMaintenanceTaskDTO> scheduledTasks) {
		this.scheduledTasks = scheduledTasks;
	}

	public List<ScheduledMaintenancePartDTO> getParts() {
		return parts;
	}

	public void setParts(List<ScheduledMaintenancePartDTO> parts) {
		this.parts = parts;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public List<ScheduledMaintenanceFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<ScheduledMaintenanceFileDTO> files) {
		this.files = files;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
