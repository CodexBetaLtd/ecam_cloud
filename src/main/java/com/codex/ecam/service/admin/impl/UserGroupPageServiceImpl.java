package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.dao.admin.UserGroupPageDao;
import com.codex.ecam.dto.app.WigetDTO;
import com.codex.ecam.model.admin.UserGroupPage;
import com.codex.ecam.model.admin.UserGroupPagePermission;
import com.codex.ecam.service.admin.api.UserGroupPageService;
import com.codex.ecam.service.app.api.AppService;

@Service
public class UserGroupPageServiceImpl implements UserGroupPageService {

	@Autowired
	private UserGroupPageDao userGroupPageDao;
	
	@Autowired
	private AppService appService;

	@Override
	public List<PagePermission> findPagePermissionByUserGroupAndPage(Page page, Integer userGroupId) {
		List<PagePermission> permissionList = new ArrayList<PagePermission>();

		if ((userGroupId != null) && (userGroupId > 0)) {
			UserGroupPage groupPage = userGroupPageDao.findByUserGroupIdAndPage(userGroupId, page);
			if (groupPage != null) {
				for (UserGroupPagePermission permission : groupPage.getPermissionList()) {
						permissionList.add(permission.getPagePermission());
				}
			}
		}

		return permissionList;
	}

	@Override
	public List<PagePermission> findPagePermissionByUserLevel(Page page) {
		List<PagePermission> permissionList = new ArrayList<PagePermission>();
		if(page.equals(Page.DASHBOARD_VIEW)){
			
			try {
				for(WigetDTO dto:appService.findAllWigetByUserLevel()){
					switch(dto.getWidget()){
					case ASSET_WIGET:permissionList.add(PagePermission.HAS_ASSET_WIGET);
					break;
					case MAINTAINACE_WIGET:permissionList.add(PagePermission.HAS_MAINTAINACE_WIGET);
					break;
					case INVENTORY_WIGET:permissionList.add(PagePermission.HAS_INVENTORY_WIGET);
					break;
					case NOTIFICATION_WIGET:permissionList.add(PagePermission.HAS_NOTIFICATION_WIGET);
					break;
					case BIZ_WIGET:permissionList.add(PagePermission.HAS_BIZ_WIGET);
					break;
					case SETTINGS_WIGET:permissionList.add(PagePermission.HAS_SETTINGS_WIGET);
					break;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			
			for(PagePermission pagePermission:PagePermission.getPagePermissionsByPage(page)){
				permissionList.add(pagePermission);

			};

		}
		
		return permissionList;
	}
	
	

}
