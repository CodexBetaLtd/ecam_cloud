package com.codex.ecam.model.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_user_group_menu_sub_menu")
public class UserGroupMenuSubMenu extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1053680102961147275L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO, generator="user_group_menu_sub_menu_s" )
	@SequenceGenerator( name="user_group_menu_sub_menu_s", sequenceName="user_group_menu_sub_menu_s", allocationSize=1 )
	@Column(name="id")
	private Integer id;
	
	@JoinColumn( name="user_group_menu_id" )
	@ManyToOne( targetEntity = UserGroupMenu.class, fetch = FetchType.LAZY )
	private UserGroupMenu userGroupMenu;
	
	@Column(name="sub_menu_id")
	private SubMenu subMenu;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserGroupMenu getUserGroupMenu() {
		return userGroupMenu;
	}

	public void setUserGroupMenu(UserGroupMenu userGroupMenu) {
		this.userGroupMenu = userGroupMenu;
	}

	@Enumerated(EnumType.ORDINAL)
	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}

}
