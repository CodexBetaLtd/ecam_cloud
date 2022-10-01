package com.codex.ecam.mappers.admin;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.constants.Widgets;
import com.codex.ecam.dto.admin.PermisonTreeDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
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

		final UserGroupDTO dto = new UserGroupDTO();
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

		final UserGroupDTO dto = domainToDto(domain);
		final List<String> p = new ArrayList<>();
		int i=0;
		for (final Menu menu : Menu.getMenus()) {
			int k=0;
			for(final SubMenu subMenu:SubMenu.getSubMenuByMenu(menu)){
				int j=0;
				for(final Page page:Page.findPageBySubMenu(subMenu)){
					int n=0;
					for(final PagePermission pagePermission:PagePermission.getPagePermissionsByPage(page)){
						for(final UserGroupPage groupPage:domain.getPageList()){
							for(final UserGroupPagePermission userGroupPagePermission:groupPage.getPermissionList()){
								if(userGroupPagePermission.getPagePermission().equals(pagePermission)){
									p.add(i+"-"+k+"-"+j+"-"+n);
								}
							}
						}
						n++;
					}
					j++;
				}
				k++;
			}
			i++;
		}

		dto.setPermissionList(p);

		setMenuPermissions(domain, dto);
		setPagePermissions(domain, dto);
		setWigetList(domain,dto);
		return dto;
	}

	private void setMenuPermissions(UserGroup domain, UserGroupDTO dto) {
		if (domain.getMenuList() != null && domain.getMenuList().size() > 0) {
			final List<Menu> topMenus = new ArrayList<Menu>();
			final List<SubMenu> subMenus = new ArrayList<SubMenu>();

			for (final UserGroupMenu menu : domain.getMenuList()) {

				topMenus.add(menu.getMenu());

				for (final UserGroupMenuSubMenu subMenu : menu.getSubMenuList()) {
					subMenus.add(subMenu.getSubMenu());
				}
			}

			dto.setTopMenus(topMenus);
			dto.setSubMenus(subMenus);
		}
	}

	private void setPagePermissions(UserGroup domain, UserGroupDTO dto) {
		if ( domain.getPageList() != null && domain.getPageList().size() > 0 ) {

			final List<PagePermission> pagePermissions = new ArrayList<PagePermission>();

			for (final UserGroupPage userGroupPage : domain.getPageList()) {
				for (final UserGroupPagePermission userGroupPagePermission : userGroupPage.getPermissionList()) {
					pagePermissions.add(userGroupPagePermission.getPagePermission());
				}
			}

			dto.setPagePermissions(pagePermissions);
		}
	}

	private void setWigetList(UserGroup domain,UserGroupDTO dto){
		final List<Widgets> widgets=new ArrayList<>();
		for (final UserGroupWiget wiget : domain.getWigetList()) {
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

		if (dto.getTopMenus() != null && dto.getTopMenus().size() > 0) {
			UserGroupMenu groupMenu;
			for (final Menu menu : dto.getTopMenus()) {
				groupMenu = createUserGroupMenu(domain, menu);

				if (dto.getSubMenus() != null && dto.getSubMenus().size() > 0) {
					addSubMenuToUserGroup(dto, groupMenu, menu);
				}
				domain.getMenuList().add(groupMenu);
			}
		}

		//		if (dto.getPage() != null) {
		//		if (dto.getPagePermissions() != null && dto.getPagePermissions().size() > 0) {
		//			final UserGroupPage groupPage = createUserGroupPage(domain, dto.getPage());
		//			addPagePermissionToUserGroup(dto, groupPage, dto.getPage());
		//			domain.getPageList().add(groupPage);
		//		}
		//		}
	}



	private void addSubMenuToUserGroup(UserGroupDTO dto, UserGroupMenu groupMenu, Menu menu) {
		UserGroupMenuSubMenu menuSubMenu;
		for (final SubMenu subMenu : dto.getSubMenus()) {
			if (subMenu.getMenu().equals(menu)) {
				menuSubMenu = createUserGroupSubMenu(groupMenu, subMenu);
				groupMenu.getSubMenuList().add(menuSubMenu);
			}
		}
	}

	private UserGroupMenuSubMenu createUserGroupSubMenu(UserGroupMenu groupMenu, SubMenu subMenu) {
		final UserGroupMenuSubMenu menuSubMenu = new UserGroupMenuSubMenu();
		menuSubMenu.setSubMenu(subMenu);
		menuSubMenu.setUserGroupMenu(groupMenu);
		menuSubMenu.setIsDeleted(false);
		return menuSubMenu;
	}

	private UserGroupMenu createUserGroupMenu(UserGroup domain, Menu menu) {
		final UserGroupMenu groupMenu = new UserGroupMenu();
		groupMenu.setMenu(menu);
		groupMenu.setUserGroup(domain);
		groupMenu.setIsDeleted(false);
		return groupMenu;
	}



	//	private void addPagePermissionToUserGroup(UserGroupDTO dto, UserGroupPage groupPage, Page page) {
	//		UserGroupPagePermission groupPagePermission;
	//		for (final PagePermission permissions : dto.getPagePermissions()) {
	//			if (permissions.getPage().equals(page)) {
	//				groupPagePermission = createUserGroupPagePermission(groupPage, permissions);
	//				groupPage.getPermissionList().add(groupPagePermission);
	//			}
	//		}
	//	}
	//
	//	private UserGroupPage createUserGroupPage(UserGroup domain, Page page) {
	//		final UserGroupPage groupPage = new UserGroupPage();
	//		groupPage.setPage(page);
	//		groupPage.setUserGroup(domain);
	//		groupPage.setIsDeleted(false);
	//		return groupPage;
	//	}

	//	private UserGroupPagePermission createUserGroupPagePermission(UserGroupPage groupPage, PagePermission permission) {
	//		final UserGroupPagePermission groupPagePermission = new UserGroupPagePermission();
	//		groupPagePermission.setPagePermission(permission);
	//		groupPagePermission.setUserGroupPage(groupPage);
	//		groupPagePermission.setIsDeleted(false);
	//		return groupPagePermission;
	//	}

	@Override
	public UserGroupDTO domainToDtoForDataTable(UserGroup domain) throws Exception {
		final UserGroupDTO dto = new UserGroupDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
