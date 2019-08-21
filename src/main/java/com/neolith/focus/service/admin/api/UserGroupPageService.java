package com.neolith.focus.service.admin.api;

import java.util.List;

import com.neolith.focus.constants.Page;
import com.neolith.focus.constants.PagePermission;

public interface UserGroupPageService {

	List<PagePermission> findPagePermissionByUserGroupAndPage(Page page, Integer userGroupId);

}
