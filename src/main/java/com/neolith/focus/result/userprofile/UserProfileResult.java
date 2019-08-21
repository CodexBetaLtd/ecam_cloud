package com.neolith.focus.result.userprofile;

import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.result.BaseResult;

public class UserProfileResult extends BaseResult<User, UserDTO> {
	
    public UserProfileResult(User domain, UserDTO dto) {
        super(domain, dto);
    }
    
    @Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}
