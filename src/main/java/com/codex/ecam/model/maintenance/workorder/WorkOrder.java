package com.codex.ecam.model.maintenance.workorder;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.codex.ecam.listeners.workorder.WorkOrderLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.model.maintenance.ChargeDepartment;
import com.codex.ecam.model.maintenance.MaintenanceType;
import com.codex.ecam.model.maintenance.Priority;
import com.codex.ecam.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;
import com.codex.ecam.model.maintenance.project.Project;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;


@Entity
@Table(name="tbl_wo")
@EntityListeners( { WorkOrderLogListener.class } )
public class WorkOrder extends BaseModel {

	private static final long serialVersionUID = 8003244413156374615L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="workorder_s")
	@SequenceGenerator(name="workorder_s", sequenceName="workorder_s", allocationSize=1)
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

	@JoinColumn( name="account_id" )
	@ManyToOne( targetEntity = Account.class, fetch = FetchType.LAZY)
	private Account account;

	@JoinColumn( name="charge_department_id" )
	@ManyToOne( targetEntity = ChargeDepartment.class, fetch = FetchType.LAZY)
	private ChargeDepartment chargeDepartment;

	@JoinColumn( name="completed_by_user_id" )
	@ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private User completedByUser;

	@JoinColumn( name="requested_by_user_id" )
	@ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private User requestedByUser;

	@JoinColumn( name="maintenance_type_id" )
	@ManyToOne(targetEntity = MaintenanceType.class, fetch = FetchType.LAZY)
	private MaintenanceType maintenanceType;

	@JoinColumn( name="priority_id" )
	@ManyToOne(targetEntity = Priority.class, fetch = FetchType.LAZY)
	private Priority priority;

	@JoinColumn(name = "project_id")
	@ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
	private Project project;

	@Column(name="work_order_request_id")
	private Integer workOrderRequestId;

	@JoinColumn(name = "scheduled_maintenance_id")
	@ManyToOne(targetEntity = ScheduledMaintenance.class, fetch = FetchType.LAZY)
	private ScheduledMaintenance scheduledMaintenance;

	@JoinColumn(name="current_status_id")  
	@ManyToOne(targetEntity = WorkOrderNotes.class, fetch = FetchType.LAZY)
	private WorkOrderNotes currentStatus;

	@Column(name="rca_action_id")
	private Integer RCAActionId;

	@Column(name="rca_cause_id")
	private Integer RCACauseId;

	@Column(name="rca_problem_id")
	private Integer RCAProblemId;

	@Column(name="dwoe_notifications_sent")
	private Integer DWOENotificationsSent;

	@Column(name="work_order_status_group")
	private Integer workOrderStatusGroup;

	@Column(name="admin_notes")
	private String adminNotes;

	@Column(name="completion_notes")
	private String completionNotes;

	@Column(name="description")
	private String description;

	@Column(name="instruction")
	private String instruction;

	@Column(name="email_user_guest")
	private String emailUserGuest;

	@Column(name="name_user_guest")
	private String nameUserGuest;

	@Column(name="phone_user_guest")
	private String phoneUserGuest;

	@Column(name="problem")
	private String problem;

	@Column(name="root_cause")
	private String rootCause;

	@Column(name="solution")
	private String solution;

	@Column(name="for_date")
	@Temporal(TemporalType.DATE)
	private Date forDate;

	@Column(name="date_completed")
	@Temporal(TemporalType.DATE)
	private  Date dateCompleted;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name="suggested_completion_date")
	@Temporal(TemporalType.DATE)
	private  Date suggestedCompletionDate;

	@Column(name="asset_production_time")
	private Double assetProductionTime;

	@Column(name="suggested_time")
	private Double suggestedTime;

	@Column(name="time_estimated_hours")
	private Double timeEstimatedHours;

	@Column(name="time_spent_hours")
	private Double timeSpentHours;

	@Column(name="total_maint_hours_offline")
	private Double totalMaintHoursOffline;

	@Column(name="total_maint_hours_online")
	private Double totalMaintHoursOnline;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderAsset> workOrderAssets;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderMiscellaneousExpense> workOrderMiscellaneousExpenses;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderNotification> workOrderNotifications;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderMeterReading> workOrderMeterReadings;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderTask> workOrderTasks;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true )
	private Set<WorkOrderPart> workOrderParts;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<WorkOrderLog> workOrderLogs;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderFile> workOrderFiles;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrderNotes> workOrderNotes;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
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

	public Set<WorkOrderAsset> getWorkOrderAssets() {
		return workOrderAssets;
	}

	public void setWorkOrderAssets(Set<WorkOrderAsset> workOrderAssets) {
		updateCollection("workOrderAssets", workOrderAssets);
	}


	public Set<WorkOrderMiscellaneousExpense> getWorkOrderMiscellaneousExpenses() {
		return workOrderMiscellaneousExpenses;
	}

	public void setWorkOrderMiscellaneousExpenses(Set<WorkOrderMiscellaneousExpense> workOrderMiscellaneousExpenses) {
		updateCollection("workOrderMiscellaneousExpenses", workOrderMiscellaneousExpenses);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ChargeDepartment getChargeDepartment() {
		return chargeDepartment;
	}

	public void setChargeDepartment(ChargeDepartment chargeDepartment) {
		this.chargeDepartment = chargeDepartment;
	}

	public User getCompletedByUser() {
		return completedByUser;
	}

	public void setCompletedByUser(User completedByUser) {
		this.completedByUser = completedByUser;
	}

	public User getRequestedByUser() {
		return requestedByUser;
	}

	public void setRequestedByUser(User requestedByUser) {
		this.requestedByUser = requestedByUser;
	}

	public MaintenanceType getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(MaintenanceType maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<WorkOrderNotification> getWorkOrderNotifications() {
		return workOrderNotifications;
	}

	public void setWorkOrderNotifications(Set<WorkOrderNotification> workOrderNotifications) {
		updateCollection("workOrderNotifications", workOrderNotifications);
	}

	public Set<WorkOrderMeterReading> getWorkOrderMeterReadings() {
		return workOrderMeterReadings;
	}

	public void setWorkOrderMeterReadings(Set<WorkOrderMeterReading> workOrderMeterReadings) {
		updateCollection("workOrderMeterReadings", workOrderMeterReadings);
	}

	public Set<WorkOrderTask> getWorkOrderTasks() {
		return workOrderTasks;
	}

	public void setWorkOrderTasks(Set<WorkOrderTask> workOrderTasks) {
		updateCollection("workOrderTasks", workOrderTasks);
	}

	public Set<WorkOrderLog> getWorkOrderLogs() {
		return workOrderLogs;
	}

	public void setWorkOrderLogs(Set<WorkOrderLog> workOrderLogs) {
		updateCollection("workOrderLogs", workOrderLogs);
	}

	public Set<WorkOrderPart> getWorkOrderParts() {
		return workOrderParts;
	}

	public void setWorkOrderParts(Set<WorkOrderPart> workOrderParts) {
		updateCollection("workOrderParts", workOrderParts);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Set<WorkOrderFile> getWorkOrderFiles() {
		return workOrderFiles;
	}

	public void setWorkOrderFiles(Set<WorkOrderFile> workOrderFiles) {
		updateCollection("workOrderFiles", workOrderFiles);
	}

	public ScheduledMaintenance getScheduledMaintenance() {
		return scheduledMaintenance;
	}

	public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
		this.scheduledMaintenance = scheduledMaintenance;
	}

	public WorkOrderNotes getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(WorkOrderNotes currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Set<WorkOrderNotes> getWorkOrderNotes() {
		return workOrderNotes;
	}

	public void setWorkOrderNotes(Set<WorkOrderNotes> workOrderNotes) {
		updateCollection("workOrderNotes", workOrderNotes);
	}

}
