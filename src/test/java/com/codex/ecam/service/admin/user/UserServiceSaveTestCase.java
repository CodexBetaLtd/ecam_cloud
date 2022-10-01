package com.codex.ecam.service.admin.user;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.mappers.admin.UserMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.admin.UserSite;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class UserServiceSaveTestCase extends TestCase {

	protected final String RESULT_IS_ERROR = "isError";

    @Autowired
    public UserDao userDao;

    //UserDTO userDTO = UserData.USER_DUMMY.getUserDTO();
    UserDTO userDTO = UserDummyData.getInstance().getDummyUserDTO();
	User user = new User();
	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			Assert.assertNotNull("UserDTO is NULL.",userDTO);
			User user = new User();
			if(userDTO != null){
				testSetUserSite(user,userDTO);
			}
			testSaveOrUpdate(userDTO,user);
			userDao.delete(user.getId());
		} catch (Exception e) {
			isError = true;
		}
	}


	@SuppressWarnings("unused")
	protected void testSetUserSite(User user, UserDTO userDTO) {

		Assert.assertNotNull("userDTO is null.", userDTO);
		Assert.assertThat(userDTO.getAssignedUserSites(), is(not(empty())));

		Set<UserSite> userSites = new HashSet<UserSite>();
		for (Integer userSiteId : userDTO.getAssignedUserSites()) {
			UserSite userSite = new UserSite();
			userSite.setIsDeleted(Boolean.FALSE);
			userSite.setUser(user);
			userSite.setSite(UserDummyData.getInstance().getDummyAsset());
			userSites.add(userSite);

			//Todo: This should not test here
			Assert.assertNotNull("UserSite is NULL.", userSite);
		}
		user.setUserSites(userSites);
		Assert.assertThat(user.getUserSites(), is(not(empty())));
		Assert.assertThat(user.getUserSites(), both(not(empty())).and(notNullValue()));

	}

	protected void testSaveOrUpdate(UserDTO dto, User domain) throws Exception {
		UserMapper.getInstance().dtoToDomain(dto, domain);

		Assert.assertEquals("Full Name Should Be Equal", domain.getFullName(), dto.getFullName());
		Assert.assertEquals("E-mail Should Be Equal", domain.getEmailAddress(), dto.getEmailAddress());

		//Todo: check all fields are Equal
		user = userDao.save(domain);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
