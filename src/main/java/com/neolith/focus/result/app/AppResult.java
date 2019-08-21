package com.neolith.focus.result.app;

import com.neolith.focus.dto.app.AppDTO;
import com.neolith.focus.model.app.App;
import com.neolith.focus.result.BaseResult;

public class AppResult extends BaseResult<App, AppDTO> {

    public AppResult(App domain, AppDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
