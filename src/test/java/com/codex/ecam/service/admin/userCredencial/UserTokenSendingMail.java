package com.codex.ecam.service.admin.userCredencial;

import com.codex.ecam.common.TestCase;

public class UserTokenSendingMail extends TestCase {


	
	protected final String RESULT_IS_ERROR = "isError";
	private boolean isError;
	@Override
	protected void executeTest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void checkActualResults() throws Exception {
		setActualResult(RESULT_IS_ERROR, isError);
		
	}

}
