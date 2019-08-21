package com.neolith.focus.controller.maintenance.workorder;


import com.neolith.focus.dto.maintenance.workOrder.WorkOrderNotificationDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.maintenance.api.WorkOrderNotificationService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(WorkOrderNotificationRestController.REQUEST_MAPPING_URL)
public class WorkOrderNotificationRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/workOrderNotification";

    @Autowired
    private WorkOrderNotificationService workOrderNotificationService; 

    @RequestMapping(value = "/woNotificationByWorkOrder", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderNotificationDTO> findAllWorkOrderNotificationByWorkOrder(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
        return workOrderNotificationService.findAllWorkOrderNotificationByWorkOrderId(dataTablesInput, id);
    }

    @RequestMapping(value = "/findWorkOrderNotification", method = RequestMethod.GET)
    public WorkOrderNotificationDTO findAllWorkOrderNotificationById(@Valid Integer id) throws Exception {
        return workOrderNotificationService.findWorkOrderNotificationById(id);
    }


}
