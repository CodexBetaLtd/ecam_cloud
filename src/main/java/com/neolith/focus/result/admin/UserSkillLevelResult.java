package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.UserSkillLevelDTO;
import com.neolith.focus.model.admin.UserSkillLevel;
import com.neolith.focus.result.BaseResult;

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
