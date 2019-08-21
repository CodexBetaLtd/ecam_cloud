package com.neolith.focus.service.log.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderLogDTO;
import com.neolith.focus.repository.FocusDataTablesInput; 

public interface WorkOrderLogService {
	
	DataTablesOutput<WorkOrderLogDTO> findAllByWorkOrderId(FocusDataTablesInput input, Integer id) throws Exception;
	
}
