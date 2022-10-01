package com.codex.ecam.service.admin.userCredencial;

import com.codex.ecam.dto.admin.UserCredentialDTO;

public class UserCredencialDummyData {

	private static UserCredencialDummyData instance = null;
	
	public static UserCredencialDummyData getInstance() {
		if (instance == null) {
			instance = new UserCredencialDummyData();
		}
		return instance;
	}
	
	public UserCredentialDTO getDummyDTOUserCredencial() {	
		UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
		userCredentialDTO.setUserName("Test User Name");
		userCredentialDTO.setPassword("Test Password");
		userCredentialDTO.setUserId(1);
				
		return userCredentialDTO;
}
	
}
