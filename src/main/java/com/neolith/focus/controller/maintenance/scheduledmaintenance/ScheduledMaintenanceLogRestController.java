package com.neolith.focus.controller.maintenance.scheduledmaintenance;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceLogDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.log.api.ScheduledMaintenanceLogService;

@RestController
@RequestMapping(ScheduledMaintenanceLogRestController.REQUEST_MAPPING)
public class ScheduledMaintenanceLogRestController {

	public static final String REQUEST_MAPPING = "restapi/scheduledmaintenancelog";

	@Autowired
	private ScheduledMaintenanceLogService smLogService;

	@RequestMapping(value = "/table-data-by-scheduled-maintenance", method = RequestMethod.GET)
	public DataTablesOutput<ScheduledMaintenanceLogDTO> findAll(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
		return smLogService.findAllByScheduledMaintenanceId(dataTablesInput, id);
	}
}
