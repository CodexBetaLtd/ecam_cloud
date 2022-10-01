package com.codex.ecam.result.maintenance;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.result.BaseResult;

public class ScheduledMaintenanceResult extends BaseResult<ScheduledMaintenance, ScheduledMaintenanceDTO> {

    public ScheduledMaintenanceResult(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
        super(domain, dto);
    }
    
    @Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
