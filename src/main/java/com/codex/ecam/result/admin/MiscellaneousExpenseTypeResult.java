package com.codex.ecam.result.admin;

import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.codex.ecam.result.BaseResult;

public class MiscellaneousExpenseTypeResult extends BaseResult<MiscellaneousExpenseType, MiscellaneousExpenseTypeDTO>{

	public MiscellaneousExpenseTypeResult(MiscellaneousExpenseType domain, MiscellaneousExpenseTypeDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}