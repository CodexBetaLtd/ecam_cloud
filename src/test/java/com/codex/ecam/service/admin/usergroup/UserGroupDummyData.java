package com.codex.ecam.service.admin.usergroup;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;

public class UserGroupDummyData {
	
	private static UserGroupDummyData instance = null;
	
	private UserGroupDummyData(){
	}
	
	public static UserGroupDummyData getInstance() {
		if (instance == null) {
			instance = new UserGroupDummyData();
		}
		return instance;
	}
	
	public UserGroupDTO getDummyDTOUserGroup() {
		
		UserGroupDTO userGroupDto = new UserGroupDTO();
		userGroupDto.setName("Test Group");
		userGroupDto.setDescription("Description");		
		
		List<Menu> menuList = new ArrayList<>(2);
		menuList.add(Menu.SETTINGS);
		menuList.add(Menu.ASSETS);
		userGroupDto.setTopMenus(menuList);
		
		List<SubMenu> subMenuList = new ArrayList<>(3);
		subMenuList.add(SubMenu.USER_GROUPS);
		subMenuList.add(SubMenu.USERS);
		subMenuList.add(SubMenu.ALL_ASSETS);		
		userGroupDto.setSubMenus(subMenuList);
		
		userGroupDto.setPage(Page.USER_GROUPS_ADD);
		
		List<PagePermission> pagePermissionList = new ArrayList<>(2);
		pagePermissionList.add(PagePermission.HAS_NEW_BUTTON_USER_GROUP_VIEW);
		pagePermissionList.add(PagePermission.HAS_SAVE_BUTTON_USER_GROUP_VIEW);		
		userGroupDto.setPagePermissions(pagePermissionList);
				
		return userGroupDto;
	}
}
