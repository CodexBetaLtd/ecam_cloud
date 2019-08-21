package com.codex.ecam.service.admin.api;

import java.util.List;

import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;

public interface UserGroupPageService {

	List<PagePermission> findPagePermissionByUserGroupAndPage(Page page, Integer userGroupId);

}
