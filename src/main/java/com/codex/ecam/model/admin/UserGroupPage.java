package com.codex.ecam.model.admin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.Page;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_user_group_page")
public class UserGroupPage extends BaseModel implements Serializable {

	private static final long serialVersionUID = 771028823625526817L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_group_page_s")
	@SequenceGenerator(name="user_group_page_s", sequenceName="user_group_page_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="user_group_id" )
	@ManyToOne( targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	private UserGroup userGroup;

	@Column(name="page_id")
	private Page page;

	@OneToMany( mappedBy = "userGroupPage", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserGroupPagePermission> permissionList = new HashSet<UserGroupPagePermission>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Set<UserGroupPagePermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(Set<UserGroupPagePermission> permissionList) {
		updateCollection("permissionList", permissionList);
	}

}
