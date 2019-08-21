package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.dao.admin.UserGroupPageDao;
import com.codex.ecam.model.admin.UserGroupPage;
import com.codex.ecam.model.admin.UserGroupPagePermission;
import com.codex.ecam.service.admin.api.UserGroupPageService;

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
