package com.neolith.focus.service.admin.passwordResetToken;

import com.neolith.focus.common.TestCase;
import com.neolith.focus.controller.UserPasswordResetController;
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
