package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.UserTokenDTO;
import com.codex.ecam.model.admin.UserToken;

public class UserTokenMapper {

	private static UserTokenMapper instance = null;
	
	private UserTokenMapper(){
	}
	
	public static UserTokenMapper getInstance() {
		if (instance == null) {
			instance = new UserTokenMapper();
		}
		return instance;
	}

    public UserTokenDTO domainToDto(UserToken domain) throws Exception {
        UserTokenDTO dto = new UserTokenDTO();
		if(domain!= null){
			dto.setId(domain.getId());
			dto.setResetPasswordToken(domain.getResetPasswordToken()); 
			dto.setResetPasswordExpires(domain.getResetPasswordExpires());
			dto.setUserId(domain.getUser().getId());
		}
		return dto;
	}

	public void dtoToDomain(UserTokenDTO dto, UserToken domain) throws Exception {
		domain.setResetPasswordToken(dto.getResetPasswordToken()); 
		domain.setResetPasswordExpires(dto.getResetPasswordExpires());
	}

}
