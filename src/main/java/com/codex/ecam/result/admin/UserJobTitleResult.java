package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.UserJobTitleDTO;
import com.codex.ecam.model.admin.UserJobTitle;
import com.codex.ecam.result.BaseResult;

public class UserJobTitleResult extends BaseResult<UserJobTitle, UserJobTitleDTO> {

	public UserJobTitleResult(UserJobTitle domain, UserJobTitleDTO dto) {
		super(domain, dto);
		}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());		
	}
}
