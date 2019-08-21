package com.codex.ecam.security;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.security.impl.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceLoginTestCase extends TestCase {

	@Autowired
	private UserDetailServiceImpl userDetailService; // the class to be tested
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// conditions
	protected final String CONDITION_USERNAME = "userName";
	protected final String CONDITION_PASSWORD = "password";

	protected final String RESULT_IS_ERROR = "isError";

	private boolean isError;

	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
	}

	protected void executeTest() {
		try {
			String userName = (String) getTestCondition(CONDITION_USERNAME);
			String password = (String) getTestCondition(CONDITION_PASSWORD);
			UserDetails user = userDetailService.loadUserByUsername(userName);
			if(user != null && passwordEncoder.matches(password, user.getPassword())){
				isError = false;
			} else {
				isError = true; 
			}
		} catch (Exception e) {
			isError = true;
		}
	}

}
