package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.cmmssetting.UserSiteDTO;
import com.neolith.focus.model.admin.UserSite;
import com.neolith.focus.result.BaseResult;

public class UserSiteResult extends BaseResult<UserSite, UserSiteDTO> {

    public UserSiteResult(UserSite domain, UserSiteDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {

    }

}
