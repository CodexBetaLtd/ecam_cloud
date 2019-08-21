package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.ChargeDepartmentDTO;
import com.neolith.focus.model.maintenance.ChargeDepartment;
import com.neolith.focus.result.BaseResult;

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