package com.codex.ecam.model.admin;

import javax.persistence.*;

import com.codex.ecam.constants.UserLevel;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.AssetUser;
import com.codex.ecam.model.biz.business.Business;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="tbl_user")
public class User extends BaseModel implements Serializable {

	private static final long serialVersionUID = -819248302938019714L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_s")
	@SequenceGenerator(name="user_s", sequenceName="user_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="full_Name")
	private String fullName;

	@Column(name="address")
	private String address;

	@Column(name="image_path")
	private String imagePath;

	@Column(name="business_user_private_key")
	private String businessUserPrivateKey;

	@Column(name="default_login_location")
	private String defaultLoginLocation;

	@Column(name="email_address")
	private String emailAddress;

	@Column(name="personal_code")
	private String personalCode;

	@Column(name="notes")
	private String notes;

	@Column(name="telephone_1")
	private String telephone1;

	@Column(name="telephone_2")
	private String telephone2;

	@Column(name="user_title")
	private String userTitle;

	@Column(name="notify_expire_days_before")
	private Integer notifyExpireDaysBefore;

	@Column(name="sys_code")
	private Integer sysCode;

	@Column(name="user_approve_status")
	private Integer userApproveStatus;

	@Column(name="user_status")
	private Integer userStatus;

	@Column(name="hourly_rate")
	private Double hourlyRate;

	@Column(name="internal_mail_all_msg")
	private Boolean internalMailAllMsg;

	@Column(name="business_approve_status")
	private Boolean businessApproveStatus;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="api_user")
	private Boolean apiUser;

	@Column(name = "email_notification")
	private Boolean emailNotification;

	@Column(name="email_system_error")
	private Boolean emailSystemError;

	@Column(name="notify_on_DWOE")
	private Boolean notifyOnDWOE;

	@Column(name="notify_on_DOAsset")
	private Boolean notifyOnDOAsset;

	@Column(name="notify_on_WOAssigned")
	private Boolean notifyOnWOAssigned;

	@Column(name="notify_on_WOCompleted")
	private Boolean notifyOnWOCompleted;

	@Column(name="notify_on_WODraft")
	private Boolean notifyOnWODraft;

	@Column(name="notify_on_WOOnHold")
	private Boolean notifyOnWOOnHold;

	@Column(name="notify_on_WOOpen")
	private Boolean notifyOnWOOpen;

	@Column(name="notify_on_WORequested")
	private Boolean notifyOnWORequested;

	@Column(name="notify_on_WOWith_NoAsset")
	private Boolean notifyOnWOWithNoAsset;

	@Column(name="notfiy_on_WOwork_InProgress")
	private Boolean notfiyOnWOworkInProgress;

	@Column(name="welcome_email_sent")
	private Boolean welcomeEmailSent;

	@Column(name="send_mail_on_expire")
	private Boolean sendMailOnExpire;

	@Column(name="user_level")
	private UserLevel userLevel;

	@Column(name="business_approve_date")
	@Temporal(TemporalType.DATE)
	private Date businessApproveDate;

	@Column(name="acc_expiry_date")
	@Temporal(TemporalType.DATE)
	private Date accExpiryDate;

	@Column(name="approval_expiry_date")
	@Temporal(TemporalType.DATE)
	private Date approvalExpiryDate;

	@Column(name="request_date")
	@Temporal(TemporalType.DATE)
	private Date requestDate;

	@Column(name="last_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@Column(name="user_approve_date")
	@Temporal(TemporalType.DATE)
	private Date userApproveDate;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn( name="user_skill_level_id" )
	@ManyToOne( targetEntity = UserSkillLevel.class, fetch = FetchType.LAZY)
	private UserSkillLevel userSkillLevel;

	@JoinColumn( name="user_job_title_id" )
	@ManyToOne( targetEntity = UserJobTitle.class, fetch = FetchType.LAZY)
	private UserJobTitle userJobTitel;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private UserCredential userCredential;

	@JoinColumn(name="currency_id")
	@ManyToOne( targetEntity = Currency.class, fetch = FetchType.LAZY)
	private Currency currency;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<UserSite> userSites;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetUser> assetUsers;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<UserCertification> userCertifications;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getBusinessUserPrivateKey() {
		return businessUserPrivateKey;
	}

	public void setBusinessUserPrivateKey(String businessUserPrivateKey) {
		this.businessUserPrivateKey = businessUserPrivateKey;
	}

	public String getDefaultLoginLocation() {
		return defaultLoginLocation;
	}

	public void setDefaultLoginLocation(String defaultLoginLocation) {
		this.defaultLoginLocation = defaultLoginLocation;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getNotifyExpireDaysBefore() {
		return notifyExpireDaysBefore;
	}

	public void setNotifyExpireDaysBefore(Integer notifyExpireDaysBefore) {
		this.notifyExpireDaysBefore = notifyExpireDaysBefore;
	}

	public Integer getSysCode() {
		return sysCode;
	}

	public void setSysCode(Integer sysCode) {
		this.sysCode = sysCode;
	}

	public Integer getUserApproveStatus() {
		return userApproveStatus;
	}

	public void setUserApproveStatus(Integer userApproveStatus) {
		this.userApproveStatus = userApproveStatus;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Boolean getInternalMailAllMsg() {
		return internalMailAllMsg;
	}

	public void setInternalMailAllMsg(Boolean internalMailAllMsg) {
		this.internalMailAllMsg = internalMailAllMsg;
	}

	public Boolean getBusinessApproveStatus() {
		return businessApproveStatus;
	}

	public void setBusinessApproveStatus(Boolean businessApproveStatus) {
		this.businessApproveStatus = businessApproveStatus;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public Boolean getApiUser() {
		return apiUser;
	}

	public void setApiUser(Boolean apiUser) {
		this.apiUser = apiUser;
	}

	public Boolean getEmailSystemError() {
		return emailSystemError;
	}

	public void setEmailSystemError(Boolean emailSystemError) {
		this.emailSystemError = emailSystemError;
	}

	public Boolean getNotifyOnDWOE() {
		return notifyOnDWOE;
	}

	public void setNotifyOnDWOE(Boolean notifyOnDWOE) {
		this.notifyOnDWOE = notifyOnDWOE;
	}

	public Boolean getNotifyOnDOAsset() {
		return notifyOnDOAsset;
	}

	public void setNotifyOnDOAsset(Boolean notifyOnDOAsset) {
		this.notifyOnDOAsset = notifyOnDOAsset;
	}

	public Boolean getNotifyOnWOAssigned() {
		return notifyOnWOAssigned;
	}

	public void setNotifyOnWOAssigned(Boolean notifyOnWOAssigned) {
		this.notifyOnWOAssigned = notifyOnWOAssigned;
	}

	public Boolean getNotifyOnWOCompleted() {
		return notifyOnWOCompleted;
	}

	public void setNotifyOnWOCompleted(Boolean notifyOnWOCompleted) {
		this.notifyOnWOCompleted = notifyOnWOCompleted;
	}

	public Boolean getNotifyOnWODraft() {
		return notifyOnWODraft;
	}

	public void setNotifyOnWODraft(Boolean notifyOnWODraft) {
		this.notifyOnWODraft = notifyOnWODraft;
	}

	public Boolean getNotifyOnWOOnHold() {
		return notifyOnWOOnHold;
	}

	public void setNotifyOnWOOnHold(Boolean notifyOnWOOnHold) {
		this.notifyOnWOOnHold = notifyOnWOOnHold;
	}

	public Boolean getNotifyOnWOOpen() {
		return notifyOnWOOpen;
	}

	public void setNotifyOnWOOpen(Boolean notifyOnWOOpen) {
		this.notifyOnWOOpen = notifyOnWOOpen;
	}

	public Boolean getNotifyOnWORequested() {
		return notifyOnWORequested;
	}

	public void setNotifyOnWORequested(Boolean notifyOnWORequested) {
		this.notifyOnWORequested = notifyOnWORequested;
	}

	public Boolean getNotifyOnWOWithNoAsset() {
		return notifyOnWOWithNoAsset;
	}

	public void setNotifyOnWOWithNoAsset(Boolean notifyOnWOWithNoAsset) {
		this.notifyOnWOWithNoAsset = notifyOnWOWithNoAsset;
	}

	public Boolean getNotfiyOnWOworkInProgress() {
		return notfiyOnWOworkInProgress;
	}

	public void setNotfiyOnWOworkInProgress(Boolean notfiyOnWOworkInProgress) {
		this.notfiyOnWOworkInProgress = notfiyOnWOworkInProgress;
	}

	public Boolean getWelcomeEmailSent() {
		return welcomeEmailSent;
	}

	public void setWelcomeEmailSent(Boolean welcomeEmailSent) {
		this.welcomeEmailSent = welcomeEmailSent;
	}

	public Boolean getSendMailOnExpire() {
		return sendMailOnExpire;
	}

	public void setSendMailOnExpire(Boolean sendMailOnExpire) {
		this.sendMailOnExpire = sendMailOnExpire;
	}

	public Date getBusinessApproveDate() {
		return businessApproveDate;
	}

	public void setBusinessApproveDate(Date businessApproveDate) {
		this.businessApproveDate = businessApproveDate;
	}

	public Date getAccExpiryDate() {
		return accExpiryDate;
	}

	public void setAccExpiryDate(Date accExpiryDate) {
		this.accExpiryDate = accExpiryDate;
	}

	public Date getApprovalExpiryDate() {
		return approvalExpiryDate;
	}

	public void setApprovalExpiryDate(Date approvalExpiryDate) {
		this.approvalExpiryDate = approvalExpiryDate;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getUserApproveDate() {
		return userApproveDate;
	}

	public void setUserApproveDate(Date userApproveDate) {
		this.userApproveDate = userApproveDate;
	}

	public Set<UserSite> getUserSites() {
		return userSites;
	}

	public void setUserSites(Set<UserSite> userSites) {
		updateCollection("userSites", userSites);
	}

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public UserCredential getUserCredential() {
		return userCredential;
	}

	public void setUserCredential(UserCredential userCredential) {
		this.userCredential = userCredential;
	}

	public void setUserCredentials(UserCredential userCredentials) {
		userCredential = userCredentials;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<AssetUser> getAssetUsers() {
		return assetUsers;
	}

	public void setAssetUsers(Set<AssetUser> assetUsers) {
		updateCollection("assetUsers", assetUsers);
	}

	public Set<UserCertification> getUserCertifications() {
		return userCertifications;
	}

	public void setUserCertifications(Set<UserCertification> userCertifications) {
		updateCollection("userCertifications", userCertifications);
	}

	public UserSkillLevel getUserSkillLevel() {
		return userSkillLevel;
	}

	public void setUserSkillLevel(UserSkillLevel userSkillLevel) {
		this.userSkillLevel = userSkillLevel;
	}

	public UserJobTitle getUserJobTitel() {
		return userJobTitel;
	}

	public void setUserJobTitel(UserJobTitle userJobTitel) {
		this.userJobTitel = userJobTitel;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Boolean getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}
}
