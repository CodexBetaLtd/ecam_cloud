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
public class UserServiceUpdateTestCase extends TestCase{

    protected final String RESULT_IS_ERROR = "isError";
    protected User user = new User();
    @Autowired
	private UserDao userDao;
	//protected UserDTO oldUserDTO = new UserDTO();
	//protected UserDTO newUserDTO = new UserDTO();
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
			UserDTO oldUserDTO = userService.findUserById(user.getId());
			UserDTO newUserDTO = userService.findUserById(user.getId());
			//testSavedUserEqualFetchUser();
			updateUser(newUserDTO);
            userService.save(newUserDTO);
            //Fetch
            UserDTO tempNewUserDTO = userService.findUserById(user.getId());
			testUserHasBeenUpdated(oldUserDTO,tempNewUserDTO);
		} catch (Exception e) {
			isError = true;
		}
		finally {
			userDao.delete(user.getId());
		}
	}




	protected void testUserHasBeenUpdated(UserDTO tempOldUserDTO,UserDTO tempNewUserDTO){
		Assert.assertNotEquals("User Full Name Cannot Equal.", tempOldUserDTO.getFullName(),tempNewUserDTO.getFullName());
		Assert.assertNotEquals("User Address Cannot Equal.", tempOldUserDTO.getAddress(),tempNewUserDTO.getAddress());
	}



	protected void updateUser(UserDTO newUserDTO){
		newUserDTO.setFullName(newUserDTO.getFullName()+"--Updated");
		newUserDTO.setAddress(newUserDTO.getAddress()+"--Updated");
	}






}
