package com.neolith.focus.result.maintenance;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.neolith.focus.result.BaseResult;

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
