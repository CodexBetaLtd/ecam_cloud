package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.PriorityDTO;
import com.neolith.focus.model.maintenance.Priority;
import com.neolith.focus.result.BaseResult;

public class PriorityResult extends BaseResult<Priority, PriorityDTO>{

	public PriorityResult(Priority domain, PriorityDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}