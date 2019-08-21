package com.codex.ecam.service.admin.passwordResetToken;

import com.codex.ecam.dto.admin.UserTokenDTO;

public class UserResetTokenDummyData {

	private static UserResetTokenDummyData instance = null;
	
	public static UserResetTokenDummyData getInstance() {
		if (instance == null) {
			instance = new UserResetTokenDummyData();
		}
		return instance;
	}
	
	public UserTokenDTO getDummyDTOUserToken() {
		
		UserTokenDTO userTokenDTO = new UserTokenDTO();
		userTokenDTO.setResetPasswordToken("Test Password reset Token");
		userTokenDTO.setUserId(1);
	//	userTokenDTO.setResetPasswordExpires(2012-12-12) ;		
			
				
		return userTokenDTO;
}
	
}
