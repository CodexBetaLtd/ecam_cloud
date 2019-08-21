package com.codex.ecam.service.admin.user;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.service.admin.api.UserService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceFindAllTestCase extends TestCase{
	
	@Autowired
	private UserService userService;
	
	protected final String CONDITION_EXPECTED_COUNT = "expectedCount";
	protected final String CONDITION_EXPECTED_FIRST_ID = "firstId";
	protected final String CONDITION_EXPECTED_FIRST_NAME = "firstName";
	protected final String CONDITION_EXPECTED_SECOND_ID = "secondId";
	protected final String CONDITION_EXPECTED_SECOND_NAME = "secondName";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;



	public static String NOT_NULL = "List Is NULL.";



	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}




	@Override
	protected void executeTest() throws Exception {
		try {
			List<UserDTO> userDTOList = userService.findAll();

			Integer expectedCount = (Integer) getTestCondition(CONDITION_EXPECTED_COUNT);
			Integer expectedFirstId = (Integer) getTestCondition(CONDITION_EXPECTED_FIRST_ID);
			Integer expectedSecondId = (Integer) getTestCondition(CONDITION_EXPECTED_SECOND_ID);
	
			Assert.assertNotNull(NOT_NULL, userDTOList);
			Assert.assertEquals("Number of User should be " + expectedCount + ".", expectedCount, new Integer(userDTOList.size()));



	        for (UserDTO userDTO : userDTOList) {
	            if (expectedFirstId.equals(userDTO.getId())) {
					testUserOne(userDTO);
	            } else if (expectedSecondId.equals(userDTO.getId())) {
					testUserTwo(userDTO);
	            }
	        }



		} catch (Exception e) {
			isError = true;
		}
	}

	private void testUserTwo(UserDTO userDTO) {
		testUserGroup(userDTO, (String) getTestCondition(CONDITION_EXPECTED_SECOND_NAME));
	}

	private void testUserOne(UserDTO userDTO) {
		testUserGroup(userDTO, (String) getTestCondition(CONDITION_EXPECTED_FIRST_NAME));
	}





    private void testUserGroup(UserDTO userDTO, String name) {
        Assert.assertNotNull("userDTO is null.", userDTO);
        Assert.assertEquals("firstName", name, userDTO.getFullName());
    }
}
