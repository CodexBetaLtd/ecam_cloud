package com.codex.ecam.service.admin.passwordResetToken;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.UserTokenDTO;
import com.codex.ecam.service.admin.api.UserTokenService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class UserTokenServiceDeleteByTestCase extends TestCase{
	

	@Autowired
	private UserTokenService  userTokenService;
	
	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		try {
			
			UserTokenDTO userTokenDTO = UserResetTokenDummyData.getInstance().getDummyDTOUserToken();
			
			Integer expectedId = userTokenService.save(userTokenDTO);
			userTokenService.delete(expectedId);
	
			Assert.assertNull("User Token is not null.", userTokenService.findById(expectedId));		

		} catch (Exception e) {
			isError = true;
		}
	}

}
