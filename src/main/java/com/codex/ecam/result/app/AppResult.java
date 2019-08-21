package com.codex.ecam.result.app;

import com.codex.ecam.dto.app.AppDTO;
import com.codex.ecam.model.app.App;
import com.codex.ecam.result.BaseResult;

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
