package com.neolith.focus.controller.maintenance.workorder;


import com.neolith.focus.dto.maintenance.workOrder.WorkOrderTaskDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.maintenance.api.WorkOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(WorkOrderTaskRestController.REQUEST_MAPPING_URL)
public class WorkOrderTaskRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/workOrderTask";

    @Autowired
    private WorkOrderTaskService workOrderTaskService;


    @RequestMapping(value = "/woTaskByWorkOrder", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderTaskDTO> findAllWorkOrderTaskByWorkOrder(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
        return workOrderTaskService.findAllWorkOrderTaskByWorkOrderId(dataTablesInput, id);
    }

    @RequestMapping(value = "/findWorkOrderTask", method = RequestMethod.GET)
    public WorkOrderTaskDTO findAllWorkOrderTaskById(@Valid Integer id) throws Exception {
        return workOrderTaskService.findWorkOrderTaskById(id);
    }


}
