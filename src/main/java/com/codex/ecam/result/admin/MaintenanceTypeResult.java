package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.model.maintenance.MaintenanceType;
import com.codex.ecam.result.BaseResult;

public class MaintenanceTypeResult extends BaseResult<MaintenanceType, MaintenanceTypeDTO>{

	public MaintenanceTypeResult(MaintenanceType domain, MaintenanceTypeDTO dto) {
		super(domain, dto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());		
	}
}
