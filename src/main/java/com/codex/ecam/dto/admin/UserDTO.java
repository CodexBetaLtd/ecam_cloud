package com.codex.ecam.dto.admin;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.UserLevel;
import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;

public class UserDTO extends BaseDTO {

	// @NotBlank(message = "Email is required")
	// @Email(message = "incorrect email address")
	// @NotBlank(message = "Name is required")
	private Integer id;
	private String fullName;
	private String address;
	private String emailAddress;
	private String personalCode;
	private String notes;
	private String telephone1;
	private String telephone2;
	private String userTitle;
	private Integer jobTitle;
	private String jobTitleName;
	private Integer skillLevel;
	private String skillLevelName;
	private Integer currencyId;

	private Integer businessId;
    private String businessName;

	private String imagePath;
	private String currentPassword;
	private String newPassword;
	private Boolean changePassword;

	private Double hourlyRate;
	private Boolean isActive;
	private UserLevel userLevel;

	private Boolean emailNotification;
	//	private Boolean emailAllAlert;
//	private Boolean emailAllMsg;
	private Boolean emailSystemError;

	private Boolean welcomeEmailSent;
	private Boolean internalMailAllMsg;
	private Boolean sendMailOnExpire;

	private Integer userGroupId;

	private UserCredentialDTO userCredentialDTO = new UserCredentialDTO();

	private List<Integer> assignedUserSites = new ArrayList<Integer>();
	private List<Integer> assignedBusinessList = new ArrayList<Integer>();
	private List<UserGroupDTO> userGroupDTOList = new ArrayList<UserGroupDTO>();
	private List<UserSiteDTO> userSiteDTOList =  new ArrayList<UserSiteDTO>();
	private List<UserLevel> userLevelList = UserLevel.getUserTypeList();
	private List<UserCertificationDTO> useCertificationDTOs = new ArrayList<UserCertificationDTO>();


	// Getters And Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	public List<UserGroupDTO> getUserGroupDTOList() {
		return userGroupDTOList;
	}

	public void setUserGroupDTOList(List<UserGroupDTO> userGroupDTOList) {
		this.userGroupDTOList = userGroupDTOList;
	}

	public List<Integer> getAssignedUserSites() {
		return assignedUserSites;
	}

	public void setAssignedUserSites(List<Integer> assignedUserSites) {
		this.assignedUserSites = assignedUserSites;
	}

	public Boolean getEmailSystemError() {
		return emailSystemError;
	}

	public void setEmailSystemError(Boolean emailSystemError) {
		this.emailSystemError = emailSystemError;
	}

	public Boolean getWelcomeEmailSent() {
		return welcomeEmailSent;
	}

	public void setWelcomeEmailSent(Boolean welcomeEmailSent) {
		this.welcomeEmailSent = welcomeEmailSent;
	}

	public Boolean getInternalMailAllMsg() {
		return internalMailAllMsg;
	}

	public void setInternalMailAllMsg(Boolean internalMailAllMsg) {
		this.internalMailAllMsg = internalMailAllMsg;
	}

	public Boolean getSendMailOnExpire() {
		return sendMailOnExpire;
	}

	public void setSendMailOnExpire(Boolean sendMailOnExpire) {
		this.sendMailOnExpire = sendMailOnExpire;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public List<UserLevel> getUserLevelList() {
		return userLevelList;
	}

	public void setUserLevelList(List<UserLevel> userLevelList) {
		this.userLevelList = userLevelList;
	}

	public List<Integer> getAssignedBusinessList() {
		return assignedBusinessList;
	}

	public void setAssignedBusinessList(List<Integer> assignedBusinessList) {
		this.assignedBusinessList = assignedBusinessList;
	}

	public List<UserSiteDTO> getUserSiteDTOList() {
		return userSiteDTOList;
	}

	public void setUserSiteDTOList(List<UserSiteDTO> userSiteDTOList) {
		this.userSiteDTOList = userSiteDTOList;
	}

	public UserCredentialDTO getUserCredentialDTO() {
		return userCredentialDTO;
	}

	public void setUserCredentialDTO(UserCredentialDTO userCredentialDTO) {
		this.userCredentialDTO = userCredentialDTO;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public Boolean getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public List<UserCertificationDTO> getUseCertificationDTOs() {
		return useCertificationDTOs;
	}

	public void setUseCertificationDTOs(List<UserCertificationDTO> useCertificationDTOs) {
		this.useCertificationDTOs = useCertificationDTOs;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Integer getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(Integer jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public Integer getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getSkillLevelName() {
		return skillLevelName;
	}

	public void setSkillLevelName(String skillLevelName) {
		this.skillLevelName = skillLevelName;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

	public Boolean getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(Boolean emailNotification) {
		this.emailNotification = emailNotification;
	}
}
