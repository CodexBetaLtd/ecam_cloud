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
public class UserServiceFindByIdTestCase extends TestCase {


	protected final String CONDITION_ID = "id";
	protected final String RESULT_IS_ERROR = "isError";
	protected User user = new User();
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	private void preCondition(){
		 user = userDao.save(UserDummyData.getInstance().getDummyUser());
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			Integer conditionId = (Integer) getTestCondition(CONDITION_ID);
			UserDTO userDTO = new UserDTO();
			if(conditionId != null && conditionId.compareTo(0)>0){
				userDTO = userService.findUserById(conditionId);
			}else {
				preCondition();
				Assert.assertNotNull("Pre Condition(Saved User) Should Be Satisfied.", user);
				Assert.assertNotNull("Pre Condition(Saved User Id) Should Be Satisfied.", user.getId());
				userDTO = userService.findUserById(user.getId());
			}

			testFetchUserNotNull(userDTO);
			testSavedUserEqualFetchUser(userDTO,user);
			postCondition();
		} catch (Exception e) {
			isError = true;
		}
	}



	private void postCondition(){
		if(user!= null && user.getId()!= null) userDao.delete(user.getId());
	}




	protected void testFetchUserNotNull(UserDTO userDTO){
		Assert.assertNotNull("Pre Condition(Saved User Id) Should Be Satisfied.", userDTO);
	}



	protected void testSavedUserEqualFetchUser(UserDTO userDTO,User user){
		Assert.assertEquals("Name Should be Equals", userDTO.getFullName(), user.getFullName());
		Assert.assertEquals("Email Should be Equals", userDTO.getEmailAddress(), user.getEmailAddress());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
