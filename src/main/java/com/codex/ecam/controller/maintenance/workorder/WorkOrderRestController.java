package com.codex.ecam.controller.maintenance.workorder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.maintenance.api.WorkOrderService;

import javax.validation.Valid;

@RestController
@RequestMapping(WorkOrderRestController.REQUEST_MAPPING_URL)
public class WorkOrderRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/workorder";

    @Autowired
    private WorkOrderService workOrderService;

    @RequestMapping(value = "/tableData", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderDTO> getWorkOrders(@Valid FocusDataTablesInput dataTablesInput) throws Exception {
        return workOrderService.findAll(dataTablesInput);
    }

    @RequestMapping(value = "/tabledata-by-business", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderDTO> getWorkOrdersByBusiness(@Valid FocusDataTablesInput dataTablesInput, Integer id) throws Exception {
        return workOrderService.findAllByBusiness(dataTablesInput, id);
    }
    
    @RequestMapping(value = "/tableDataByProject", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderDTO> findAllWorkOrderByProject(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
    	return workOrderService.findAllByProjectId(dataTablesInput, id);
    }
    
    @RequestMapping(value = "/table-data-by-date/{date}", method = RequestMethod.GET)
    public DataTablesOutput<WorkOrderDTO> findWorkOrderByDate(@Valid FocusDataTablesInput dataTablesInput, @PathVariable("date") String date) throws Exception {
    	return workOrderService.findWorkOrdersByDate(dataTablesInput, date);
    }

}
