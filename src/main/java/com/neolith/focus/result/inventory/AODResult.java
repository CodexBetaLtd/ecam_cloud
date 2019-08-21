package com.neolith.focus.result.inventory;

import com.neolith.focus.dto.inventory.aod.AODDTO;
import com.neolith.focus.model.inventory.aod.AOD;
import com.neolith.focus.result.BaseResult;

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
