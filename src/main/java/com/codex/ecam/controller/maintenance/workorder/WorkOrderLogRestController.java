package com.codex.ecam.controller.maintenance.workorder;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderLogDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.log.api.WorkOrderLogService;

@RestController
@RequestMapping(WorkOrderLogRestController.REQUEST_MAPPING_URL)
public class WorkOrderLogRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/workorderlog";

	@Autowired
	private WorkOrderLogService workOrderLogService;

	@RequestMapping(value = "/tableDataByWorkOrder", method = RequestMethod.GET)
	public DataTablesOutput<WorkOrderLogDTO> findAllWorkOrderByWorkOrder(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
		return workOrderLogService.findAllByWorkOrderId(dataTablesInput, id);
	}

}
