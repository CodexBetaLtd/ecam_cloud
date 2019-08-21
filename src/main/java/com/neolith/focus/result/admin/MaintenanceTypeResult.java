package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.MaintenanceTypeDTO;
import com.neolith.focus.model.maintenance.MaintenanceType;
import com.neolith.focus.result.BaseResult;

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
