package com.codex.ecam.controller.maintenance.scheduledmaintenance;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceService;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerService;

import javax.validation.Valid;

@RestController
@RequestMapping(ScheduledMaintenanceRestController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/scheduledmaintenance";

    @Autowired
    private ScheduledMaintenanceService scheduledMaintenanceService;
    
    @Autowired
    private ScheduledMaintenanceTriggerService scheduledMaintenanceTriggerService;

    @RequestMapping(value = "/tableData", method = RequestMethod.GET)
    public DataTablesOutput<ScheduledMaintenanceDTO> findAllScheduledMaintenance(@Valid FocusDataTablesInput input) throws Exception {
        return scheduledMaintenanceService.findAll(input);
    }
    
    @RequestMapping(value = "/tableDataByProject", method = RequestMethod.GET)
    public DataTablesOutput<ScheduledMaintenanceDTO> findAllScheduledMaintenanceByProject(@Valid FocusDataTablesInput input, @Valid Integer id) throws Exception {
    	return scheduledMaintenanceService.findAllByProjectId(input, id);
    }

    @RequestMapping(value = "/upcoming-scheduled-maintenance", method = RequestMethod.GET)
    public DataTablesOutput<ScheduledMaintenanceTriggerDTO> findUpComingScheduleMaintenance(@Valid FocusDataTablesInput input) {
    	
    	try {
    		return scheduledMaintenanceTriggerService.findUpcomingScheduleMaintenance(input);			
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
    }

}
