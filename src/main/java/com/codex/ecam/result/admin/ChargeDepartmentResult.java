package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.model.maintenance.ChargeDepartment;
import com.codex.ecam.result.BaseResult;

public class ChargeDepartmentResult extends BaseResult<ChargeDepartment, ChargeDepartmentDTO>{

	public ChargeDepartmentResult(ChargeDepartment domain, ChargeDepartmentDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}