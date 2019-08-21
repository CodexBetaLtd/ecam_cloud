package com.neolith.focus.service.admin.user;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.service.admin.api.UserService;
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
