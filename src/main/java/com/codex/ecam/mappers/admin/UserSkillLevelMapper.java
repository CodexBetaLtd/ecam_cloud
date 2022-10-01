package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.UserSkillLevelDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.UserSkillLevel;

public class UserSkillLevelMapper extends GenericMapper<UserSkillLevel, UserSkillLevelDTO> {


	private static UserSkillLevelMapper instance = null;

	private  UserSkillLevelMapper() {

	}

	public static UserSkillLevelMapper getInstance() {
		if (instance == null) {
			instance = new UserSkillLevelMapper();
		}
		return instance;
	}

	@Override
	public UserSkillLevelDTO domainToDto(UserSkillLevel domain) throws Exception {
		UserSkillLevelDTO dto =new UserSkillLevelDTO();
		dto.setId(domain.getId());
		dto.setSkill(domain.getSkill());
		dto.setDescription(domain.getDescription());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public UserSkillLevelDTO domainToDtoForDataTable(UserSkillLevel domain) throws Exception {
		UserSkillLevelDTO dto =new UserSkillLevelDTO();
		dto.setId(domain.getId());
		dto.setSkill(domain.getSkill());
		dto.setDescription(domain.getDescription()); 

		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

	@Override
	public void dtoToDomain(UserSkillLevelDTO dto, UserSkillLevel domain) throws Exception {
		domain.setId(dto.getId());
		domain.setSkill(dto.getSkill());
		domain.setDescription(dto.getDescription());
		setCommanDomainFields(dto, domain);

	}

}
