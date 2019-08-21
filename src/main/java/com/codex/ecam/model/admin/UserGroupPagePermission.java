package com.codex.ecam.model.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_user_group_page_permission")
public class UserGroupPagePermission extends BaseModel implements Serializable {

	private static final long serialVersionUID = -1780021543989243480L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO, generator="user_group_page_permission_s" )
	@SequenceGenerator( name="user_group_page_permission_s", sequenceName="user_group_page_permission_s", allocationSize=1 )
	@Column(name="id")
	private Integer id;
	
	@JoinColumn( name="user_group_page_id" )
	@ManyToOne( targetEntity = UserGroupPage.class, fetch = FetchType.LAZY )
	private UserGroupPage userGroupPage;
	
	@Column(name="page_permission_id")
	private PagePermission pagePermission;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserGroupPage getUserGroupPage() {
		return userGroupPage;
	}

	public void setUserGroupPage(UserGroupPage userGroupPage) {
		this.userGroupPage = userGroupPage;
	}

	public PagePermission getPagePermission() {
		return pagePermission;
	}

	public void setPagePermission(PagePermission pagePermission) {
		this.pagePermission = pagePermission;
	}

}
