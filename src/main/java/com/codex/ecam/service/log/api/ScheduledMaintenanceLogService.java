package com.codex.ecam.service.log.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceLogDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface ScheduledMaintenanceLogService {

	DataTablesOutput<ScheduledMaintenanceLogDTO> findAllByScheduledMaintenanceId(FocusDataTablesInput input, Integer id) throws Exception;

}
