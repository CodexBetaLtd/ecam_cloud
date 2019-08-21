package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderNotificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface WorkOrderNotificationService {

    DataTablesOutput<WorkOrderNotificationDTO> findAllWorkOrderNotificationByWorkOrderId(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

    WorkOrderNotificationDTO findWorkOrderNotificationById(Integer workOrderId) throws Exception;

}
