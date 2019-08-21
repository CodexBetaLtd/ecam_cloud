package com.neolith.focus.result.admin;

import com.neolith.focus.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.neolith.focus.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.neolith.focus.result.BaseResult;

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