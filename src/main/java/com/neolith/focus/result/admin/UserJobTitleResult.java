package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.UserJobTitleDTO;
import com.neolith.focus.model.admin.UserJobTitle;
import com.neolith.focus.result.BaseResult;

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
