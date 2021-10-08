package com.codex.ecam.dto.maintenance.workOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;

public class WorkOrderDTO extends BaseDTO {

	private Integer id;
	private Integer businessId;
	private Integer siteId;
	private Integer projectId;
	private Integer priorityId;
	private Integer maintenanceTypeId;
	private Integer workOrderRequestId;
	private Integer scheduledMaintenanceId;
	private Integer accountId;
	private Integer chargeDepartmentId;
	private Integer completedByUserId;
	private Integer requestedByUserId;
	private Integer RCAActionId;
	private Integer RCACauseId;
	private Integer RCAProblemId;
	private Integer currentStatusId;
	private Integer DWOENotificationsSent;
	private Integer workOrderStatusGroup;

	private String description;
	private String businessName;
	private String instruction;
	private String emailUserGuest;
	private String nameUserGuest;
	private String phoneUserGuest;
	private String adminNotes;
	private String completionNotes;
	private String problem;
	private String rootCause;
	private String solution;
	private String projectName;
	private String completedByUserName;
	private String requestedByUserName;
	private String code;
	private String assetNameStr;
	private String priorityName;
	private String maintenanceTypeName;
	private String chargeDepartmentName;
	private String accountName;

	private Date forDate;
	private Date startDate;
	private Date dateCompleted;
	private Date suggestedCompletionDate;

	private Double assetProductionTime;
	private Double suggestedTime;
	private Double timeEstimatedHours;
	private Double timeSpentHours;
	private Double totalMaintHoursOffline;
	private Double totalMaintHoursOnline;

	//Previous Details
	private WorkOrderStatus previousWorkOrderStatus;
	private WorkOrderStatus workOrderStatus = WorkOrderStatus.OPEN;

	//Used For WO Notification
	private List<Integer> statusChangeNotifyUserList;
	private List<Integer> assignmentNotifyUserList;
	private List<Integer> completionNotifyUserList;
	private List<Integer> onlineOfflineNotifyUserList;
	private List<Integer> taskCompletionNotifyUserList;

	private List<WorkOrderNotificationDTO> notifications = new ArrayList<>();
	private List<WorkOrderMeterReadingDTO> meterReadings = new ArrayList<>();
	private List<WorkOrderMeterReadingValueDTO> workOrderMeterReadingValues = new ArrayList<>();
	private List<AssetMeterReadingValueDTO> assetMeterReadingValues = new ArrayList<>();
	private List<MiscellaneousExpenseDTO> miscellaneousExpenses = new ArrayList<>();
	private List<WorkOrderTaskDTO> tasks = new ArrayList<>();
	private List<WorkOrderPartDTO> parts = new ArrayList<>();
	private List<AssetDTO> assets = new ArrayList<>();
	private List<WorkOrderFileDTO> files = new ArrayList<>();
	private List<WorkOrderNoteDTO> workOrderNoteDTOs = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssetNameStr() {
		return assetNameStr;
	}

	public void setAssetNameStr(String assetNameStr) {
		this.assetNameStr = assetNameStr;
	}

	public List<AssetDTO> getAssets() {
		return assets;
	}

	public void setAssets(List<AssetDTO> assets) {
		this.assets = assets;
	}

	public WorkOrderStatus getPreviousWorkOrderStatus() {
		return previousWorkOrderStatus;
	}

	public void setPreviousWorkOrderStatus(WorkOrderStatus previousWorkOrderStatus) {
		this.previousWorkOrderStatus = previousWorkOrderStatus;
	}

	public List<Integer> getStatusChangeNotifyUserList() {
		return statusChangeNotifyUserList;
	}

	public void setStatusChangeNotifyUserList(List<Integer> statusChangeNotifyUserList) {
		this.statusChangeNotifyUserList = statusChangeNotifyUserList;
	}

	public List<Integer> getAssignmentNotifyUserList() {
		return assignmentNotifyUserList;
	}

	public void setAssignmentNotifyUserList(List<Integer> assignmentNotifyUserList) {
		this.assignmentNotifyUserList = assignmentNotifyUserList;
	}

	public List<Integer> getCompletionNotifyUserList() {
		return completionNotifyUserList;
	}

	public void setCompletionNotifyUserList(List<Integer> completionNotifyUserList) {
		this.completionNotifyUserList = completionNotifyUserList;
	}

	public List<Integer> getOnlineOfflineNotifyUserList() {
		return onlineOfflineNotifyUserList;
	}

	public void setOnlineOfflineNotifyUserList(List<Integer> onlineOfflineNotifyUserList) {
		this.onlineOfflineNotifyUserList = onlineOfflineNotifyUserList;
	}

	public List<Integer> getTaskCompletionNotifyUserList() {
		return taskCompletionNotifyUserList;
	}

	public void setTaskCompletionNotifyUserList(List<Integer> taskCompletionNotifyUserList) {
		this.taskCompletionNotifyUserList = taskCompletionNotifyUserList;
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

	public Integer getScheduledMaintenanceId() {
		return scheduledMaintenanceId;
	}

	public void setScheduledMaintenanceId(Integer scheduledMaintenanceId) {
		this.scheduledMaintenanceId = scheduledMaintenanceId;
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

	public Integer getCompletedByUserId() {
		return completedByUserId;
	}

	public void setCompletedByUserId(Integer completedByUserId) {
		this.completedByUserId = completedByUserId;
	}

	public Integer getRequestedByUserId() {
		return requestedByUserId;
	}

	public void setRequestedByUserId(Integer requestedByUserId) {
		this.requestedByUserId = requestedByUserId;
	}

	public WorkOrderStatus getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public Integer getRCAActionId() {
		return RCAActionId;
	}

	public void setRCAActionId(Integer rCAActionId) {
		RCAActionId = rCAActionId;
	}

	public Integer getRCACauseId() {
		return RCACauseId;
	}

	public void setRCACauseId(Integer rCACauseId) {
		RCACauseId = rCACauseId;
	}

	public Integer getRCAProblemId() {
		return RCAProblemId;
	}

	public void setRCAProblemId(Integer rCAProblemId) {
		RCAProblemId = rCAProblemId;
	}

	public String getAdminNotes() {
		return adminNotes;
	}

	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompletionNotes() {
		return completionNotes;
	}

	public void setCompletionNotes(String completionNotes) {
		this.completionNotes = completionNotes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailUserGuest() {
		return emailUserGuest;
	}

	public void setEmailUserGuest(String emailUserGuest) {
		this.emailUserGuest = emailUserGuest;
	}

	public String getNameUserGuest() {
		return nameUserGuest;
	}

	public void setNameUserGuest(String nameUserGuest) {
		this.nameUserGuest = nameUserGuest;
	}

	public String getPhoneUserGuest() {
		return phoneUserGuest;
	}

	public void setPhoneUserGuest(String phoneUserGuest) {
		this.phoneUserGuest = phoneUserGuest;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Date getForDate() {
		return forDate;
	}

	public void setForDate(Date forDate) {
		this.forDate = forDate;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public Date getSuggestedCompletionDate() {
		return suggestedCompletionDate;
	}

	public void setSuggestedCompletionDate(Date suggestedCompletionDate) {
		this.suggestedCompletionDate = suggestedCompletionDate;
	}

	public Double getAssetProductionTime() {
		return assetProductionTime;
	}

	public void setAssetProductionTime(Double assetProductionTime) {
		this.assetProductionTime = assetProductionTime;
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

	public Double getTimeSpentHours() {
		return timeSpentHours;
	}

	public void setTimeSpentHours(Double timeSpentHours) {
		this.timeSpentHours = timeSpentHours;
	}

	public Double getTotalMaintHoursOffline() {
		return totalMaintHoursOffline;
	}

	public void setTotalMaintHoursOffline(Double totalMaintHoursOffline) {
		this.totalMaintHoursOffline = totalMaintHoursOffline;
	}

	public Double getTotalMaintHoursOnline() {
		return totalMaintHoursOnline;
	}

	public void setTotalMaintHoursOnline(Double totalMaintHoursOnline) {
		this.totalMaintHoursOnline = totalMaintHoursOnline;
	}

	public Integer getDWOENotificationsSent() {
		return DWOENotificationsSent;
	}

	public void setDWOENotificationsSent(Integer dWOENotificationsSent) {
		DWOENotificationsSent = dWOENotificationsSent;
	}

	public Integer getWorkOrderStatusGroup() {
		return workOrderStatusGroup;
	}

	public void setWorkOrderStatusGroup(Integer workOrderStatusGroup) {
		this.workOrderStatusGroup = workOrderStatusGroup;
	}

	public Integer getWorkOrderRequestId() {
		return workOrderRequestId;
	}

	public void setWorkOrderRequestId(Integer workOrderRequestId) {
		this.workOrderRequestId = workOrderRequestId;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCompletedByUserName() {
		return completedByUserName;
	}

	public void setCompletedByUserName(String completedByUserName) {
		this.completedByUserName = completedByUserName;
	}

	public String getRequestedByUserName() {
		return requestedByUserName;
	}

	public void setRequestedByUserName(String requestedByUserName) {
		this.requestedByUserName = requestedByUserName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<WorkOrderNotificationDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<WorkOrderNotificationDTO> notifications) {
		this.notifications = notifications;
	}

	public List<WorkOrderMeterReadingDTO> getMeterReadings() {
		return meterReadings;
	}

	public void setMeterReadings(List<WorkOrderMeterReadingDTO> meterReadings) {
		this.meterReadings = meterReadings;
	}

	public List<WorkOrderMeterReadingValueDTO> getWorkOrderMeterReadingValues() {
		return workOrderMeterReadingValues;
	}

	public void setWorkOrderMeterReadingValues(List<WorkOrderMeterReadingValueDTO> workOrderMeterReadingValues) {
		this.workOrderMeterReadingValues = workOrderMeterReadingValues;
	}

	public List<AssetMeterReadingValueDTO> getAssetMeterReadingValues() {
		return assetMeterReadingValues;
	}

	public void setAssetMeterReadingValues(List<AssetMeterReadingValueDTO> assetMeterReadingValues) {
		this.assetMeterReadingValues = assetMeterReadingValues;
	}

	public List<MiscellaneousExpenseDTO> getMiscellaneousExpenses() {
		return miscellaneousExpenses;
	}

	public void setMiscellaneousExpenses(List<MiscellaneousExpenseDTO> miscellaneousExpenses) {
		this.miscellaneousExpenses = miscellaneousExpenses;
	}

	public List<WorkOrderTaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<WorkOrderTaskDTO> tasks) {
		this.tasks = tasks;
	}

	public List<WorkOrderPartDTO> getParts() {
		return parts;
	}

	public void setParts(List<WorkOrderPartDTO> parts) {
		this.parts = parts;
	}

	public List<WorkOrderFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<WorkOrderFileDTO> files) {
		this.files = files;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getCurrentStatusId() {
		return currentStatusId;
	}

	public void setCurrentStatusId(Integer currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	public List<WorkOrderNoteDTO> getWorkOrderNoteDTOs() {
		return workOrderNoteDTOs;
	}

	public void setWorkOrderNoteDTOs(List<WorkOrderNoteDTO> workOrderNoteDTOs) {
		this.workOrderNoteDTOs = workOrderNoteDTOs;
	}

	public String getPriorityName() {
		return priorityName;
	}

	public String getMaintenanceTypeName() {
		return maintenanceTypeName;
	}

	public String getChargeDepartmentName() {
		return chargeDepartmentName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	public void setMaintenanceTypeName(String maintenanceTypeName) {
		this.maintenanceTypeName = maintenanceTypeName;
	}

	public void setChargeDepartmentName(String chargeDepartmentName) {
		this.chargeDepartmentName = chargeDepartmentName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
