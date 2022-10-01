package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.result.BaseResult;

public class UserResult extends BaseResult<User, UserDTO> {

    public UserResult(User domain, UserDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
