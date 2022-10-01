package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderTaskDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface WorkOrderTaskService {

    DataTablesOutput<WorkOrderTaskDTO> findAllWorkOrderTaskByWorkOrderId(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

    WorkOrderTaskDTO findWorkOrderTaskById(Integer workOrderId) throws Exception;

}
