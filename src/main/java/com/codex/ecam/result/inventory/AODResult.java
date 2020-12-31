package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.aod.AODDTO;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.result.BaseResult;

public class AODResult extends BaseResult<AOD, AODDTO> {

    public AODResult(AOD domain, AODDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
