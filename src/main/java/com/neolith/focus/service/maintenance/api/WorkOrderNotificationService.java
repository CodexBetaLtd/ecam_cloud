package com.neolith.focus.service.maintenance.api;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderNotificationDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface WorkOrderNotificationService {

    DataTablesOutput<WorkOrderNotificationDTO> findAllWorkOrderNotificationByWorkOrderId(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

    WorkOrderNotificationDTO findWorkOrderNotificationById(Integer workOrderId) throws Exception;

}
