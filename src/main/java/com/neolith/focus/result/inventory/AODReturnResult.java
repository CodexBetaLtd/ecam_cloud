package com.neolith.focus.result.inventory;

import com.neolith.focus.dto.inventory.aodReturn.AODReturnDTO;
import com.neolith.focus.model.inventory.aodRetun.AODReturn;
import com.neolith.focus.result.BaseResult;

public class AODReturnResult extends BaseResult<AODReturn, AODReturnDTO> {

    public AODReturnResult(AODReturn domain, AODReturnDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
