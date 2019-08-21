package com.neolith.focus.dto.admin;

import java.util.Date;

import com.neolith.focus.constants.UserCertifiCationLevel;
import com.neolith.focus.dto.BaseDTO;
import com.neolith.focus.util.DateUtil;

public class UserCertificationDTO extends BaseDTO {

	private Integer id;
	private Integer version;
	private Integer userId;
	private Date validFromDate;
	private Date validToDate;
	private UserCertifiCationLevel userCertificationLevel;
	private String userCertificationLevelName;
	private Integer certificationTypeId;
	private String certificationTypeName;
	private String description;
	private String userCertificationName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCertificationTypeId() {
		return certificationTypeId;
	}

	public void setCertificationTypeId(Integer certificationTypeId) {
		this.certificationTypeId = certificationTypeId;
	}

	public String getCertificationTypeName() {
		return certificationTypeName;
	}

	public void setCertificationTypeName(String certificationTypeName) {
		this.certificationTypeName = certificationTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserCertificationName() {
		return userCertificationName;
	}

	public void setUserCertificationName(String userCertificationName) {
		this.userCertificationName = userCertificationName;
	}

	public String getValidFromDate() {
		return DateUtil.getSimpleDateString(validFromDate);
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public String getValidToDate() {
		return DateUtil.getSimpleDateString(validToDate);
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUserCertificationLevelName() {
		return userCertificationLevelName;
	}

	public void setUserCertificationLevelName(String userCertificationLevelName) {
		this.userCertificationLevelName = userCertificationLevelName;
	}

	public UserCertifiCationLevel getUserCertificationLevel() {
		return userCertificationLevel;
	}

	public void setUserCertificationLevel(UserCertifiCationLevel userCertificationLevel) {
		this.userCertificationLevel = userCertificationLevel;
	}





}