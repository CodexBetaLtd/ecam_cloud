package com.codex.ecam.result.maintenance;

import com.codex.ecam.dto.maintenance.exworkorder.ExWorkOrderDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.model.maintenance.ExWorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.result.BaseResult;

public class ExWorkOrderResult extends BaseResult<ExWorkOrder, ExWorkOrderDTO> {

	public ExWorkOrderResult(ExWorkOrder domain, ExWorkOrderDTO dto) {
		super(domain, dto);
	}
	
	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
