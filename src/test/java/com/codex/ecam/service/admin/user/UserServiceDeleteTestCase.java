package com.codex.ecam.service.admin.user;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.service.admin.api.UserService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceDeleteTestCase extends TestCase{

    protected final String RESULT_IS_ERROR = "isError";
    protected User user = new User();
    protected UserDTO userDTO = new UserDTO();
    @Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	private void preCondition(){
		user = userDao.save(UserDummyData.getInstance().getDummyUser());
		Assert.assertNotNull("Pre Condition(Saved User) Should Be Satisfied.", user);
		Assert.assertNotNull("Pre Condition(Saved User Id) Should Be Satisfied.", user.getId());
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			 //Save User
			preCondition();

			//Fetch User
			userDTO = userService.findUserById(user.getId());
			testSavedUserEqualFetchUser(userDTO,user);

			//Delete User
            userService.delete(userDTO.getId());

			// find is null or not
			userDTO = userService.findUserById(user.getId());
			testDeleteUserSuccess(userDTO);

		} catch (Exception e) {
			isError = true;
		}
	}

	protected void testSavedUserEqualFetchUser(UserDTO userDTO,User user){
		Assert.assertEquals("Name Should be Equals", userDTO.getFullName(), user.getFullName());
		Assert.assertEquals("Email Should be Equals", userDTO.getEmailAddress(), user.getEmailAddress());
	}

	@SuppressWarnings("null")
	protected void testDeleteUserSuccess(UserDTO userDTO){
		Assert.assertNull("User Cannot Found.", userDTO);
		Assert.assertNull("User Id Cannot Found.", userDTO.getId());
	}

}
