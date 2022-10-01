package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;
import com.codex.ecam.model.admin.UserSite;
import com.codex.ecam.result.BaseResult;

public class UserSiteResult extends BaseResult<UserSite, UserSiteDTO> {

    public UserSiteResult(UserSite domain, UserSiteDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {

    }

}
