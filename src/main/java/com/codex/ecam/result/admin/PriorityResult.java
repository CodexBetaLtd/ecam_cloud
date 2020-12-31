package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.model.maintenance.Priority;
import com.codex.ecam.result.BaseResult;

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