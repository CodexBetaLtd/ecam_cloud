package com.codex.ecam.controller.maintenance.scheduledmaintenance;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ScheduledMaintenanceTaskRestController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceTaskRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/scheduledTask";

    @Autowired
    private ScheduledMaintenanceTaskService scheduledMaintenanceTaskService;

    @RequestMapping(value = "/findScheduledMaintenanceTask", method = RequestMethod.GET)
    public ScheduledMaintenanceTaskDTO findScheduledMaintenanceTaskById(@Valid Integer id) throws Exception {
        try {
            return scheduledMaintenanceTaskService.findScheduledMaintenanceTaskById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ScheduledMaintenanceTaskDTO();
        }
    }

    @RequestMapping(value = "/findScheduledTaskType", method = RequestMethod.GET)
    public List<TaskType> findScheduledTaskType() throws Exception {
        try {
            return TaskType.getTaskTypes();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    } 

    @RequestMapping(value = "/upcoming-scheduled-maintenance-task/{triggerId}", method = RequestMethod.GET)
    public DataTablesOutput<ScheduledMaintenanceTaskDTO> findUpComingScheduleMaintenanceTask(@Valid FocusDataTablesInput input, @PathVariable("triggerId") Integer triggerId) {

    	try {
    		return scheduledMaintenanceTaskService.findUpComingScheduleMaintenanceTask(input, triggerId);			
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
    } 

}
