package com.neolith.focus.model.maintenance.scheduledmaintenance;

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

import com.neolith.focus.constants.WorkOrderStatus;
import com.neolith.focus.listeners.scheduledmaintenance.ScheduledMaintenanceLogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.maintenance.Account;
import com.neolith.focus.model.maintenance.ChargeDepartment;
import com.neolith.focus.model.maintenance.MaintenanceType;
import com.neolith.focus.model.maintenance.Priority;
import com.neolith.focus.model.maintenance.project.Project;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;

@Entity
@Table(name = "tbl_scheduled_maintenance")
@EntityListeners( ScheduledMaintenanceLogListener.class )
public class ScheduledMaintenance extends BaseModel {

	private static final long serialVersionUID = 1544137802210366948L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_maintenance_s")
	@SequenceGenerator(name = "scheduled_maintenance_s", sequenceName = "scheduled_maintenance_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "account_id")
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	private Account account;

	@JoinColumn(name = "charge_department_id")
	@ManyToOne(targetEntity = ChargeDepartment.class, fetch = FetchType.LAZY)
	private ChargeDepartment chargeDepartment;

	@JoinColumn(name = "maintenance_type_id")
	@ManyToOne(targetEntity = MaintenanceType.class, fetch = FetchType.LAZY)
	private MaintenanceType maintenanceType;

	@JoinColumn(name = "priority_id")
	@ManyToOne(targetEntity = Priority.class, fetch = FetchType.LAZY)
	private Priority priority;

	@JoinColumn(name = "project_id")
	@ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
	private Project project;

	@JoinColumn(name = "requestor_user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User requestor;

	@Column(name = "start_as_wo_status_id")
	private WorkOrderStatus startAsWoStatus;

	@Column(name = "is_running")
	private Boolean isRunning;

	@Column(name = "suggested_time")
	private Double suggestedTime;

	@Column(name = "time_estimated_hours")
	private Double timeEstimatedHours;

	@Column(name = "suggested_completion")
	private Integer suggestedCompletion;

	@Column(name = "notify_technicians_when_dwoe")
	private Boolean notifyTechniciansWhenDWOE;

	@Column(name = "notify_creator_when_dwoe")
	private Boolean notifyCreatorWhenDWOE;

	@Column(name = "admin_notes")
	private String adminNotes;

	@Column(name = "code")
	private String code;

	@Column(name = "completion_notes")
	private String completionNotes;

	@Column(name = "description")
	private String description;

	@Column(name = "r_type")
	private String rType;

	@Column(name = "schedule_description")
	private String scheduleDescription;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceNotification> scheduledMaintenanceNotifications;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceAsset> scheduledMaintenanceAssets;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceUser> scheduledMaintenanceUsers;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceNesting> scheduledMaintenanceNestings;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceTrigger> scheduledMaintenanceTriggers;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ScheduledMaintenanceLog> logs;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceFile> scheduledMaintenanceFiles;

	@OneToMany(mappedBy = "scheduledMaintenance", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<WorkOrder> workOrders;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getRequestor() {
		return requestor;
	}

	public void setRequestor(User requestor) {
		this.requestor = requestor;
	}

	public WorkOrderStatus getStartAsWoStatus() {
		return startAsWoStatus;
	}

	public void setStartAsWoStatus(WorkOrderStatus startAsWoStatus) {
		this.startAsWoStatus = startAsWoStatus;
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

	public Boolean getNotifyTechniciansWhenDWOE() {
		return notifyTechniciansWhenDWOE;
	}

	public void setNotifyTechniciansWhenDWOE(Boolean notifyTechniciansWhenDWOE) {
		this.notifyTechniciansWhenDWOE = notifyTechniciansWhenDWOE;
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

	public Set<ScheduledMaintenanceAsset> getScheduledMaintenanceAssets() {
		return scheduledMaintenanceAssets;
	}

	public void setScheduledMaintenanceAssets(Set<ScheduledMaintenanceAsset> scheduledMaintenanceAssets) {
		updateCollection("scheduledMaintenanceAssets", scheduledMaintenanceAssets);
	}

	public Set<ScheduledMaintenanceUser> getScheduledMaintenanceUsers() {
		return scheduledMaintenanceUsers;
	}

	public void setScheduledMaintenanceUsers(Set<ScheduledMaintenanceUser> scheduledMaintenanceUsers) {
		updateCollection("scheduledMaintenanceUsers", scheduledMaintenanceUsers);
	}

	public Set<ScheduledMaintenanceNesting> getScheduledMaintenanceNestings() {
		return scheduledMaintenanceNestings;
	}

	public void setScheduledMaintenanceNestings(Set<ScheduledMaintenanceNesting> scheduledMaintenanceNestings) {
		updateCollection("scheduledMaintenanceNestings", scheduledMaintenanceNestings);
	}

	public Set<ScheduledMaintenanceTrigger> getScheduledMaintenanceTriggers() {
		return scheduledMaintenanceTriggers;
	}

	public void setScheduledMaintenanceTriggers(Set<ScheduledMaintenanceTrigger> scheduledMaintenanceTriggers) {
		updateCollection("scheduledMaintenanceTriggers", scheduledMaintenanceTriggers);
	}

	public Set<ScheduledMaintenanceNotification> getScheduledMaintenanceNotifications() {
		return scheduledMaintenanceNotifications;
	}

	public void setScheduledMaintenanceNotifications(Set<ScheduledMaintenanceNotification> scheduledMaintenanceNotifications) {
		updateCollection("scheduledMaintenanceNotifications", scheduledMaintenanceNotifications);
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Set<ScheduledMaintenanceLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<ScheduledMaintenanceLog> logs) {
		updateCollection("logs", logs);
	}

	public Set<ScheduledMaintenanceFile> getScheduledMaintenanceFiles() {
		return scheduledMaintenanceFiles;
	}

	public void setScheduledMaintenanceFiles(Set<ScheduledMaintenanceFile> scheduledMaintenanceFiles) {
		updateCollection("scheduledMaintenanceFiles", scheduledMaintenanceFiles);
	}

	public Set<WorkOrder> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(Set<WorkOrder> workOrders) {
		updateCollection("workOrders", workOrders);
	}

}
