package com.codex.ecam.dto.admin.usergroup;

import java.util.List;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.constants.Widgets;
import com.codex.ecam.dto.BaseDTO;

public class UserGroupDTO extends BaseDTO {

	private Integer id;
	private Integer businessId;

	private String name;
	private String businessName;
	private String description;

	private List<Menu> topMenus;
	private List<SubMenu> subMenus;
	private List<PagePermission> pagePermissions;
	private List<Widgets> wigets;
	private List<String> permissionList;

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<PagePermission> getPagePermissions() {
		return pagePermissions;
	}

	public void setPagePermissions(List<PagePermission> pagePermissions) {
		this.pagePermissions = pagePermissions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Menu> getTopMenus() {
		return topMenus;
	}

	public void setTopMenus(List<Menu> topMenus) {
		this.topMenus = topMenus;
	}

	public List<SubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SubMenu> subMenus) {
		this.subMenus = subMenus;
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

	public List<Widgets> getWigets() {
		return wigets;
	}

	public void setWigets(List<Widgets> wigets) {
		this.wigets = wigets;
	}

	public List<String> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}

}
