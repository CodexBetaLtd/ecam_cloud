package com.codex.ecam.service.admin.usergroup;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.UserGroupService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Component;

@Component
public class UserGroupServiceFindTableDataTestCase extends TestCase {

	// conditions
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_ID = "firstId";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_ID = "secondId";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";
	protected final String RESULT_IS_ERROR = "isError";
    @Autowired
    private UserGroupService userGroupService;
    private boolean isError;

    @Override
    protected void checkActualResults() throws Exception {
        setActualResult(RESULT_IS_ERROR, isError);
    }

	@Override
	protected void executeTest() throws Exception {
		try {
			DataTablesOutput<UserGroupDTO> out = userGroupService.findAll(new FocusDataTablesInput());

			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			Integer expectedFirstId = (Integer) getTestCondition(CONDITION_EXPECTED_FIRST_ID);
			Integer expectedSecondId = (Integer) getTestCondition(CONDITION_EXPECTED_SECOND_ID);

            Assert.assertTrue("User Group list is empty.", !out.getData().isEmpty());
            Assert.assertEquals("Number of User Groups should be " + expectedCount + ".", expectedCount, new Integer("" + out.getRecordsTotal()));

            for (UserGroupDTO userGroup : out.getData()) {
                if (expectedFirstId.equals(userGroup.getId())) {
                    testUserGroupOne(userGroup);
                } else if (expectedSecondId.equals(userGroup.getId())) {
                    testUserGroupTwo(userGroup);
                }
            }
        } catch (Exception e) {
            isError = true;
		}
	}

	private void testUserGroupTwo(UserGroupDTO userGroup) {
		testUserGroup(userGroup, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testUserGroupOne(UserGroupDTO userGroup) {
		testUserGroup(userGroup, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}

    private void testUserGroup(UserGroupDTO userGroup, String name) {
        Assert.assertNotNull("userGroup is null.", userGroup);
        Assert.assertEquals("firstName", name, userGroup.getName());
    }

}
