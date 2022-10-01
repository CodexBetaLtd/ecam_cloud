package com.codex.ecam.service.maintenance.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.codex.ecam.repository.FocusDataTablesInput; 

public interface ScheduledMaintenanceTaskService {

    ScheduledMaintenanceTaskDTO findScheduledMaintenanceTaskById(Integer scheduledMaintenanceId) throws Exception;

	DataTablesOutput<ScheduledMaintenanceTaskDTO> findUpComingScheduleMaintenanceTask(FocusDataTablesInput input, Integer triggerId) throws Exception;

}
