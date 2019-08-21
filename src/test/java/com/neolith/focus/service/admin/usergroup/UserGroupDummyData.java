package com.neolith.focus.service.admin.usergroup;

import com.neolith.focus.constants.Menu;
import com.neolith.focus.constants.Page;
import com.neolith.focus.constants.PagePermission;
import com.neolith.focus.constants.SubMenu;
import com.neolith.focus.dto.admin.UserGroupDTO;

import java.util.ArrayList;
import java.util.List;

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
