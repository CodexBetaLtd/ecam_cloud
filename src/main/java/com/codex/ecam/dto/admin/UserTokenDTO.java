package com.codex.ecam.dto.admin;

import java.util.Date;

public class UserTokenDTO {

	private Integer id;
	private Integer userId;
	private String resetPasswordToken;
	private Date resetPasswordExpires;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}
	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	public Date getResetPasswordExpires() {
		return resetPasswordExpires;
	}
	public void setResetPasswordExpires(Date resetPasswordExpires) {
		this.resetPasswordExpires = resetPasswordExpires;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
