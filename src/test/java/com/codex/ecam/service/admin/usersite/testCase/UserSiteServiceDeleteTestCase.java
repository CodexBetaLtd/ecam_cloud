package com.codex.ecam.service.admin.usersite.testCase;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dao.admin.UserSiteDao;
import com.codex.ecam.model.admin.UserSite;
import com.codex.ecam.result.admin.UserSiteResult;
import com.codex.ecam.service.admin.api.UserSiteService;
import com.codex.ecam.service.admin.usersite.UserSiteData;

@Component
public class UserSiteServiceDeleteTestCase extends TestCase {

	protected final String RESULT_IS_ERROR = "isError";

	@Autowired
	public UserSiteService userSiteService;

	@Autowired
	public UserSiteDao userSiteDao;

	protected UserSite userSite = new UserSite();

	protected UserSiteResult resultDetail;

	private boolean isError;

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	private void preCondition() throws Exception{
		resultDetail = userSiteService.save(UserSiteData.getInstance().getUserSiteDTO());
		Assert.assertNotNull("Pre Condition(Saved User Site) Should Be Satisfied.", resultDetail);
		Assert.assertNotNull("Pre Condition(Saved User Site Id) Should Be Satisfied.", resultDetail.getIntegerList().get(0));
		userSite = userSiteDao.findOne(resultDetail.getIntegerList().get(0));
	}

	@Override
	protected void executeTest() throws Exception {
		try {
			//Save
			preCondition();
			//Delete
			userSiteService.deleteUserSiteById(resultDetail.getIntegerList().get(0));
			// find is null or not
			userSite = userSiteDao.findOne(resultDetail.getIntegerList().get(0));
			testDeleteUserSiteSuccess(userSite);
		} catch (Exception e) {
			isError = true;
		}
	}


	protected void testDeleteUserSiteSuccess(UserSite userSite){
		Assert.assertNull("User Site Cannot Found.", userSite);
		//Assert.assertNull("User Site Id Cannot Found.", userSite.getId());
	}





}
