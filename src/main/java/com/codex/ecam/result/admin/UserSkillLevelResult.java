package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.UserSkillLevelDTO;
import com.codex.ecam.model.admin.UserSkillLevel;
import com.codex.ecam.result.BaseResult;

public class UserSkillLevelResult extends BaseResult<UserSkillLevel, UserSkillLevelDTO> {

	public UserSkillLevelResult(UserSkillLevel domain, UserSkillLevelDTO dto) {
		super(domain, dto);

	}


	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());		
	}
}
