package com.codex.ecam.mappers.admin;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.constants.Widgets;
import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.UserGroup;
import com.codex.ecam.model.admin.UserGroupMenu;
import com.codex.ecam.model.admin.UserGroupMenuSubMenu;
import com.codex.ecam.model.admin.UserGroupPage;
import com.codex.ecam.model.admin.UserGroupPagePermission;
import com.codex.ecam.model.admin.UserGroupWiget;

public class UserGroupMapper extends GenericMapper<UserGroup, UserGroupDTO> {

	private static UserGroupMapper instance = null;

	private UserGroupMapper() {
	}

	public static UserGroupMapper getInstance() {
		if (instance == null) {
			instance = new UserGroupMapper();
		}
		return instance;
	}

	@Override
	public UserGroupDTO domainToDto(UserGroup domain) throws Exception {

		UserGroupDTO dto = new UserGroupDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setVersion(domain.getVersion());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		return dto;
	}

	public UserGroupDTO domainToDtoWithPermission(UserGroup domain) throws Exception {

		UserGroupDTO dto = domainToDto(domain);

		if ((domain.getMenuList() != null) && (domain.getMenuList().size() > 0)) {
			List<Menu> topMenus = new ArrayList<Menu>();
			List<SubMenu> subMenus = new ArrayList<SubMenu>();

			for (UserGroupMenu menu : domain.getMenuList()) {

				topMenus.add(menu.getMenu());

				for (UserGroupMenuSubMenu subMenu : menu.getSubMenuList()) {
					subMenus.add(subMenu.getSubMenu());
				}
			}

			dto.setTopMenus(topMenus);
			dto.setSubMenus(subMenus);
		}
		setWigetList(domain,dto);
		return dto;
	}
	private void setWigetList(UserGroup domain,UserGroupDTO dto){
		List<Widgets> widgets=new ArrayList<>();
		for (UserGroupWiget wiget : domain.getWigetList()) {
			widgets.add(wiget.getWidgets());
		}
		dto.setWigets(widgets);
	}
	@Override
	public void dtoToDomain(UserGroupDTO dto, UserGroup domain) throws Exception {

		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());

		if ((dto.getTopMenus() != null) && (dto.getTopMenus().size() > 0)) {
			UserGroupMenu groupMenu;
			for (Menu menu : dto.getTopMenus()) {
				groupMenu = createUserGroupMenu(domain, menu);

				if ((dto.getSubMenus() != null) && (dto.getSubMenus().size() > 0)) {
					addSubMenuToUserGroup(dto, groupMenu, menu);
				}
				domain.getMenuList().add(groupMenu);
			}
		}

		if (dto.getPage() != null) {
			if ((dto.getPagePermissions() != null) && (dto.getPagePermissions().size() > 0)) {
				UserGroupPage groupPage = createUserGroupPage(domain, dto.getPage());
				addPagePermissionToUserGroup(dto, groupPage, dto.getPage());
				domain.getPageList().add(groupPage);
			}
		}
	}
	


	private void addSubMenuToUserGroup(UserGroupDTO dto, UserGroupMenu groupMenu, Menu menu) {
		UserGroupMenuSubMenu menuSubMenu;
		for (SubMenu subMenu : dto.getSubMenus()) {
			if (subMenu.getMenu().equals(menu)) {
				menuSubMenu = createUserGroupSubMenu(groupMenu, subMenu);
				groupMenu.getSubMenuList().add(menuSubMenu);
			}
		}
	}

	private UserGroupMenuSubMenu createUserGroupSubMenu(UserGroupMenu groupMenu, SubMenu subMenu) {
		UserGroupMenuSubMenu menuSubMenu = new UserGroupMenuSubMenu();
		menuSubMenu.setSubMenu(subMenu);
		menuSubMenu.setUserGroupMenu(groupMenu);
		menuSubMenu.setIsDeleted(false);
		return menuSubMenu;
	}

	private UserGroupMenu createUserGroupMenu(UserGroup domain, Menu menu) {
		UserGroupMenu groupMenu = new UserGroupMenu();
		groupMenu.setMenu(menu);
		groupMenu.setUserGroup(domain);
		groupMenu.setIsDeleted(false);
		return groupMenu;
	}
	
	

	private void addPagePermissionToUserGroup(UserGroupDTO dto, UserGroupPage groupPage, Page page) {
		UserGroupPagePermission groupPagePermission;
		for (PagePermission permissions : dto.getPagePermissions()) {
			if (permissions.getPage().equals(page)) {
				groupPagePermission = createUserGroupPagePermission(groupPage, permissions);
				groupPage.getPermissionList().add(groupPagePermission);
			}
		}
	}

	private UserGroupPage createUserGroupPage(UserGroup domain, Page page) {
		UserGroupPage groupPage = new UserGroupPage();
		groupPage.setPage(page);
		groupPage.setUserGroup(domain);
		groupPage.setIsDeleted(false);
		return groupPage;
	}

	private UserGroupPagePermission createUserGroupPagePermission(UserGroupPage groupPage, PagePermission permission) {
		UserGroupPagePermission groupPagePermission = new UserGroupPagePermission();
		groupPagePermission.setPagePermission(permission);
		groupPagePermission.setUserGroupPage(groupPage);
		groupPagePermission.setIsDeleted(false);
		return groupPagePermission;
	}

	@Override
	public UserGroupDTO domainToDtoForDataTable(UserGroup domain) throws Exception {
		UserGroupDTO dto = new UserGroupDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
