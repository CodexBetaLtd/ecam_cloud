package com.neolith.focus.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.model.BaseModel;

@Entity
@Table(name="tbl_user_credentials")
public class UserCredential extends BaseModel {

	private static final long serialVersionUID = -3414353136441389962L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_credentials_s")
	@SequenceGenerator(name="user_credentials_s", sequenceName="user_credentials_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="user_id" )
	@OneToOne( targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;

	@Column(name="user_name")
	private String userName;

	@Column(name="password")
	private String password;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
