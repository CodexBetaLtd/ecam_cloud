package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnDTO;
import com.codex.ecam.model.inventory.mrnReturn.MRNReturn;
import com.codex.ecam.result.BaseResult;

public class MRNReturnResult extends BaseResult<MRNReturn, MRNReturnDTO> {

    public MRNReturnResult(MRNReturn domain, MRNReturnDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
