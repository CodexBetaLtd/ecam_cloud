package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;
import com.codex.ecam.result.BaseResult;

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
