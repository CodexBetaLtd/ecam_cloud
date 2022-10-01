package com.codex.ecam.service.admin.passwordResetToken;

import com.codex.ecam.common.TestCase;
import com.codex.ecam.controller.UserPasswordResetController;

import org.springframework.beans.factory.annotation.Autowired;

public class UserTokenSendingMail extends TestCase {

@Autowired
UserPasswordResetController controller ;
	
	protected final String RESULT_IS_ERROR = "isError";
	private boolean isError;
	@Override
	protected void executeTest() throws Exception {

    }

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
		
	}

}
