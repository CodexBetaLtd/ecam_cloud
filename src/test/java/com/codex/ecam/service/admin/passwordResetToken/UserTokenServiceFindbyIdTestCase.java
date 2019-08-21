package com.codex.ecam.service.admin.passwordResetToken;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.UserTokenDTO;
import com.codex.ecam.service.admin.api.UserTokenService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class UserTokenServiceFindbyIdTestCase    extends TestCase{

	@Autowired
	private UserTokenService userTokenService;
	
	// conditions
	protected final String CONDITION_EXPECTED_NAME = "name";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {			
	UserTokenDTO userTokenDTO = UserResetTokenDummyData.getInstance().getDummyDTOUserToken();
			
			 expectedId = userTokenService.save(userTokenDTO);
			
			UserTokenDTO userToken= userTokenService.findById(expectedId);
	
			if( userToken != null){
				Assert.assertEquals("Asset category should be " + expectedId + ".", expectedId, userToken.getId());
				testEntity(userToken);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
			userTokenService.delete(expectedId);
		}
	}

	private void testEntity(UserTokenDTO userTokenDTO) {
		testEntity(userTokenDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(UserTokenDTO userTokenDTO , String name) {
        Assert.assertNotNull("Token is null.", userTokenDTO);        
        Assert.assertEquals("Name ", name, userTokenDTO.getResetPasswordToken());
    }

}
