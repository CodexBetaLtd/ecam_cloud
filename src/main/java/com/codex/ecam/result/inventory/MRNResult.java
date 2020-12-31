package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.result.BaseResult;

public class MRNResult extends BaseResult<MRN, MRNDTO> {

    public MRNResult(MRN domain, MRNDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
