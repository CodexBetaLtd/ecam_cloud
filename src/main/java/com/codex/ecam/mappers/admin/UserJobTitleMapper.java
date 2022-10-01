package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.UserJobTitleDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.UserJobTitle;

public class UserJobTitleMapper extends GenericMapper<UserJobTitle, UserJobTitleDTO> {


	private static UserJobTitleMapper instance = null;

	private  UserJobTitleMapper() {

	}

	public static UserJobTitleMapper getInstance() {
		if (instance == null) {
			instance = new UserJobTitleMapper();
		}
		return instance;
	}

	@Override
	public UserJobTitleDTO domainToDto(UserJobTitle domain) throws Exception {
		UserJobTitleDTO dto =new UserJobTitleDTO();
		dto.setId(domain.getId());
		dto.setJobTitle(domain.getJobTitle());
		dto.setDescription(domain.getDescription());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public UserJobTitleDTO domainToDtoForDataTable(UserJobTitle domain) throws Exception {
		UserJobTitleDTO dto =new UserJobTitleDTO();
		dto.setId(domain.getId());
		dto.setJobTitle(domain.getJobTitle());
		dto.setDescription(domain.getDescription());

		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}

		return dto;
	}

	@Override
	public void dtoToDomain(UserJobTitleDTO dto, UserJobTitle domain) throws Exception {
		domain.setId(dto.getId());
		domain.setJobTitle(dto.getJobTitle());
		domain.setDescription(dto.getDescription());
		setCommanDomainFields(dto, domain);
	}

}
