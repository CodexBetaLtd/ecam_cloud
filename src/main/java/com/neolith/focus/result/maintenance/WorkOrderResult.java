package com.neolith.focus.result.maintenance;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;
import com.neolith.focus.result.BaseResult;

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
