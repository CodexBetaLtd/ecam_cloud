package com.codex.ecam.result.maintenance;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.result.BaseResult;

public class WorkOrderResult extends BaseResult<WorkOrder, WorkOrderDTO> {

	public WorkOrderResult(WorkOrder domain, WorkOrderDTO dto) {
		super(domain, dto);
	}
	
	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
