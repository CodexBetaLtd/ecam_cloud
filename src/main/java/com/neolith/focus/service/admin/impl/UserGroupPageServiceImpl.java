package com.neolith.focus.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neolith.focus.constants.Page;
import com.neolith.focus.constants.PagePermission;
import com.neolith.focus.dao.admin.UserGroupPageDao;
import com.neolith.focus.model.admin.UserGroupPage;
import com.neolith.focus.model.admin.UserGroupPagePermission;
import com.neolith.focus.service.admin.api.UserGroupPageService;

@Service
public class UserGroupPageServiceImpl implements UserGroupPageService {

	@Autowired
	private UserGroupPageDao userGroupPageDao;

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

}
