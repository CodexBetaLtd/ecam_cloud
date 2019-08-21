package com.codex.ecam.service.admin.userCredencial;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.service.admin.api.UserCredentialService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class UserCredencialServiceFindbyUserIdTestCase    extends TestCase{

	@Autowired
	private UserCredentialService userCredentialService;
	
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
			UserCredentialDTO userCredentialDTO = UserCredencialDummyData.getInstance().getDummyDTOUserCredencial();
			expectedId=userCredentialService.save(userCredentialDTO);
			
			UserCredentialDTO userCredential= userCredentialService.findByUserId(userCredentialDTO.getUserId());
	
			if( userCredential != null){
				Assert.assertEquals("User Credential should be " + expectedId + ".", expectedId, userCredential.getId());
				testEntity(userCredential);
			} else {
				isError = true;
			}			
		} catch (Exception e) {
			isError = true;
		} finally {
			userCredentialService.delete(expectedId);
		}
	}

	private void testEntity(UserCredentialDTO userCredentialDTO ) {
		testEntity(userCredentialDTO, (String) getTestCondition(CONDITION_EXPECTED_NAME));
	}
	
    private void testEntity(UserCredentialDTO userCredentialDTO  , String name) {
        Assert.assertNotNull("User Credential is null.", userCredentialDTO);        
        Assert.assertEquals("Password ", name, userCredentialDTO.getPassword());
    }

}
