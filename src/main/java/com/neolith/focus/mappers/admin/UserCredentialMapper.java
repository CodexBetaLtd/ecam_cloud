package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.UserCredential;

public class UserCredentialMapper extends GenericMapper<UserCredential, UserCredentialDTO> {

	private static UserCredentialMapper instance = null;
	
	private UserCredentialMapper(){
	}
	
	public static UserCredentialMapper getInstance() {
		if (instance == null) {
			instance = new UserCredentialMapper();
		}
		return instance;
	}

	@Override
    public UserCredentialDTO domainToDto(UserCredential domain) throws Exception {
        if (domain != null) {
            UserCredentialDTO dto = new UserCredentialDTO();
			dto.setId(domain.getId());
			dto.setUserName(domain.getUserName());
			dto.setPassword(domain.getPassword());
			dto.setUserId(domain.getUser().getId());
			dto.setVersion(domain.getVersion());
			return dto;
		}
		return null;
	}

	@Override
    public void dtoToDomain(UserCredentialDTO dto, UserCredential domain) throws Exception {
        domain.setId(dto.getId());
        domain.setVersion(dto.getVersion());
        domain.setUserName(dto.getUserName());
        domain.setPassword(dto.getPassword());
        domain.setIsDeleted(false);


    }

    @Override
    public UserCredentialDTO domainToDtoForDataTable(UserCredential domain) throws Exception {
        UserCredentialDTO dto = new UserCredentialDTO();
		dto.setId(domain.getId());
		dto.setUserName(domain.getUserName());
		dto.setPassword(domain.getPassword());
		return dto;
    }

}
