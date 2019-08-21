package com.neolith.focus.service.admin.usergroup;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dao.admin.UserGroupMenuDao;
import com.neolith.focus.dao.admin.UserGroupMenuSubMenuDao;
import com.neolith.focus.dao.admin.UserGroupPageDao;
import com.neolith.focus.dao.admin.UserGroupPagePermissionDao;
import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.result.admin.UserGroupResult;
import com.neolith.focus.service.admin.api.UserGroupService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserGroupServiceCreateTestCase extends TestCase {
	
	// conditions
	protected final String CONDITION_SAVE_ENTITY = "entity";
	protected final String RESULT_IS_ERROR = "isError";
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserGroupMenuDao userGroupMenuDao;
	@Autowired
	private UserGroupPageDao userGroupPageDao;
	@Autowired
	private UserGroupMenuSubMenuDao userGroupMenuSubMenuDao;
	@Autowired
	private UserGroupPagePermissionDao userGroupPagePermissionDao;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
        UserGroupResult result = null;
        try {
            UserGroupDTO userGroupDTO = (UserGroupDTO) getTestCondition(CONDITION_SAVE_ENTITY);
            result = userGroupService.save(userGroupDTO);

            Assert.assertNotNull("User Group is null.", userGroupService.findById(result.getDomainEntity().getId()));
            Assert.assertThat("User Group Menu is Empty.", userGroupMenuDao.findByUserGroupId(result.getDomainEntity().getId()), Matchers.is(Matchers.not(Matchers.empty())));
            Assert.assertThat("User Group Page is Empty.", userGroupPageDao.findByUserGroupId(result.getDomainEntity().getId()), Matchers.is(Matchers.not(Matchers.empty())));
            Assert.assertThat("User Group Sub Menu is Empty.", userGroupMenuSubMenuDao.findByUserGroupId(result.getDomainEntity().getId()), Matchers.is(Matchers.not(Matchers.empty())));
            Assert.assertThat("User Group Page Permission is Empty.", userGroupPagePermissionDao.findByUserGroupId(result.getDomainEntity().getId()), Matchers.is(Matchers.not(Matchers.empty())));

        } catch (Exception e) {
            isError = true;
		} finally {
            userGroupService.delete(result.getDomainEntity().getId());
        }
    }

}
