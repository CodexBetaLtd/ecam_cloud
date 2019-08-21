package com.codex.ecam.controller.maintenance.scheduledmaintenance;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ScheduledMaintenanceTaskController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceTaskController {

    public static final String REQUEST_MAPPING_URL = "/scheduled-task"; 
    
}
