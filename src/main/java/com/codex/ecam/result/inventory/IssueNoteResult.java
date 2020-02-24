package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.inventory.issuenote.IssueNoteDTO;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.result.BaseResult;

public class IssueNoteResult extends BaseResult<AOD, IssueNoteDTO> {

    public IssueNoteResult(AOD domain, IssueNoteDTO dto) {
        super(domain, dto);
    }

    @Override
    public void updateDtoIdAndVersion() {
        getDtoEntity().setId(getDomainEntity().getId());
        getDtoEntity().setVersion(getDomainEntity().getVersion());
    }

}
