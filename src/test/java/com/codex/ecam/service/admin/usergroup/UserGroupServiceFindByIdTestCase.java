package com.codex.ecam.service.admin.usergroup;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.service.admin.api.UserGroupService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGroupServiceFindByIdTestCase extends TestCase {

	@Autowired
	private UserGroupService userGroupService;
	
	// conditions
	protected final String CONDITION_EXPECTED_ID = "id";
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {			
			Integer expectedId = (Integer) getTestCondition(CONDITION_EXPECTED_ID);			
			UserGroupDTO userGroup = userGroupService.findById(expectedId);
	
//			Assert.assertNotNull("User Group is null.", userGroup);
			if( userGroup != null){
				Assert.assertEquals("User Group should be " + expectedId + ".", expectedId, userGroup.getId());
				testUserGroup(userGroup);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		}
	}

	private void testUserGroup(UserGroupDTO userGroup) {
		testUserGroup(userGroup, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testUserGroup(UserGroupDTO userGroup, String name) {
        Assert.assertNotNull("userGroup is null.", userGroup);        
        Assert.assertEquals("Name ", name, userGroup.getName());
    }

}
