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
public class UserTokenServiceCreateTestCase extends TestCase{

	@Autowired
	private  UserTokenService  tokenService;
	
	// conditions
	protected final String CONDITION_SAVE_ENTITY = "entity";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer expectedId = null;
		try {
			 UserTokenDTO userTokenDTO = ( UserTokenDTO) getTestCondition(CONDITION_SAVE_ENTITY);
			expectedId = tokenService.save(userTokenDTO);
	
			Assert.assertNotNull("Token is null.", tokenService.findById(expectedId));		
							
		} catch (Exception e) {
			isError = true;
		} finally {
			tokenService.delete(expectedId);			
		}
	}

}
