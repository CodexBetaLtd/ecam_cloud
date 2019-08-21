package com.neolith.focus.service.maintenance.api;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderTaskDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface WorkOrderTaskService {

    DataTablesOutput<WorkOrderTaskDTO> findAllWorkOrderTaskByWorkOrderId(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

    WorkOrderTaskDTO findWorkOrderTaskById(Integer workOrderId) throws Exception;

}
