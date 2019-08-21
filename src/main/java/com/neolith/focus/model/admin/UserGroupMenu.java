package com.neolith.focus.model.admin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.constants.Menu;
import com.neolith.focus.model.BaseModel;

@Entity
@Table(name="tbl_user_group_menu")
public class UserGroupMenu extends BaseModel implements Serializable {

	private static final long serialVersionUID = 3328873791953044797L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_group_menu_s")
	@SequenceGenerator(name="user_group_menu_s", sequenceName="user_group_menu_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="user_group_id" )
	@ManyToOne( targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	private UserGroup userGroup;

	@Column(name="menu_id")
	private Menu menu;

	@OneToMany( mappedBy = "userGroupMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserGroupMenuSubMenu> subMenuList = new HashSet<UserGroupMenuSubMenu>();

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

	@Enumerated(EnumType.ORDINAL)
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Set<UserGroupMenuSubMenu> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(Set<UserGroupMenuSubMenu> subMenuList) {
		updateCollection("subMenuList", subMenuList);
	}

}
