package com.codex.ecam.service.admin.api;

import com.codex.ecam.dto.admin.UserCredentialDTO;

public interface UserCredentialService {

	UserCredentialDTO findByUserName(String username);
	
	UserCredentialDTO findByUserId(Integer useId);
	
	void update(UserCredentialDTO userCredentialDTO) throws Exception;

	Integer save(UserCredentialDTO userCredentialDTO) throws Exception;

	void delete(Integer expectedId);


}
