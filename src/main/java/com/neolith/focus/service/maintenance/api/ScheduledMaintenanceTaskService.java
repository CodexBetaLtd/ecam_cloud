package com.neolith.focus.service.maintenance.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.neolith.focus.repository.FocusDataTablesInput; 

public interface ScheduledMaintenanceTaskService {

    ScheduledMaintenanceTaskDTO findScheduledMaintenanceTaskById(Integer scheduledMaintenanceId) throws Exception;

	DataTablesOutput<ScheduledMaintenanceTaskDTO> findUpComingScheduleMaintenanceTask(FocusDataTablesInput input, Integer triggerId) throws Exception;

}
