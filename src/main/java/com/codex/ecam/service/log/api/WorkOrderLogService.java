package com.codex.ecam.service.log.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderLogDTO;
import com.codex.ecam.repository.FocusDataTablesInput; 

public interface WorkOrderLogService {
	
	DataTablesOutput<WorkOrderLogDTO> findAllByWorkOrderId(FocusDataTablesInput input, Integer id) throws Exception;
	
}
