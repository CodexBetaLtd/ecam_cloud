package com.neolith.focus.controller.maintenance.scheduledmaintenance;
 
import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.neolith.focus.repository.FocusDataTablesInput; 
import com.neolith.focus.service.maintenance.api.ScheduledMaintenanceTriggerService;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid; 

@RestController
@RequestMapping(ScheduledMaintenanceTriggerRestController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceTriggerRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/scheduled-maintenance-trigger";

    @Autowired
    private ScheduledMaintenanceTriggerService smTriggerService; 

    @RequestMapping(value = "/tasks-between-dates", method = RequestMethod.GET)
    public DataTablesOutput<ScheduledMaintenanceTriggerDTO> findTasksBetweenDates(@Valid FocusDataTablesInput dataTablesInput, String from, String to) throws Exception {
        return smTriggerService.findTasksBetweenDates(dataTablesInput, from, to);
    }

}
