package com.codex.ecam.service.admin.userCredencial;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.service.admin.api.UserCredentialService;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Component
public class UserCredencialServiceUpdateTestCase extends TestCase{
	
	public static enum FieldName {USERNAME,PASSWORD};

	@Autowired
	private UserCredentialService userCredentialService;
	
	protected final String UPDATE_ENTITY_NAME = "name";
	
	protected final String UPDATE_ENTITY_VALUE = "value";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	protected void executeTest() throws Exception {
		Integer id = null;
		try {
			
			UserCredentialDTO userCredentialDTO = UserCredencialDummyData.getInstance().getDummyDTOUserCredencial();	
			userCredentialService.save(userCredentialDTO);
		userCredentialDTO.setId(id);
		Integer userId=userCredentialDTO.getUserId();
			setGivenValue((String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE), userCredentialDTO);
			
			userCredentialService.update(userCredentialDTO);			
			UserCredentialDTO updateduserCredentialDTO= userCredentialService.findByUserId(userId);
	
			Assert.assertNotNull(" User credencial is null.", updateduserCredentialDTO);
			Assert.assertTrue("  User credencial not updated.", isFieldUpdated(updateduserCredentialDTO, (String)getTestCondition(UPDATE_ENTITY_NAME), getTestCondition(UPDATE_ENTITY_VALUE)));
					
		} catch (Exception e) {
			isError = true;
		} finally {
			userCredentialService.delete(id);
		}
	}
	
	private boolean isFieldUpdated(UserCredentialDTO userCredentialDTO  , String name, Object value) {

		if (name.equals(FieldName.USERNAME.name())) {
			return userCredentialDTO.getUserName().equals((String) value);
			
		} else if (name.equals(FieldName.PASSWORD.name())) {
			return userCredentialDTO.getPassword().equals((String) value);
			
		} 
		return false;
	}

	private void setGivenValue(String name, Object value, UserCredentialDTO userCredentialDTO  ) {

		if (name.equals(FieldName.USERNAME.name())) {
			userCredentialDTO.setUserName((String) value);
			
		} else if (name.equals(FieldName.PASSWORD.name())) {
			userCredentialDTO.setPassword((String) value);
			
		} 
	}
}
