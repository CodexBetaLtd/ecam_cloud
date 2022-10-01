package com.codex.ecam.model.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_user_token")
public class UserToken {	
	
	private static final int EXPIRATION = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	@Temporal(TemporalType.DATE)
	@Column(name = "reset_password_expires")
	private Date resetPasswordExpires;

	@JoinColumn(name = "user_id")
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public static int getExpiration() {
		return EXPIRATION;
	}

}
