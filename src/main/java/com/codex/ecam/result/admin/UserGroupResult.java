package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
import com.codex.ecam.model.admin.UserGroup;
import com.codex.ecam.result.BaseResult;

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
    
