package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.model.admin.UserGroup;
import com.neolith.focus.result.BaseResult;

public class UserGroupResult extends BaseResult<UserGroup, UserGroupDTO> {

    public UserGroupResult(UserGroup domain, UserGroupDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }
}
    
