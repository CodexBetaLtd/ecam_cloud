package com.neolith.focus.service.log.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceLogDTO;
import com.neolith.focus.repository.FocusDataTablesInput;

public interface ScheduledMaintenanceLogService {

	DataTablesOutput<ScheduledMaintenanceLogDTO> findAllByScheduledMaintenanceId(FocusDataTablesInput input, Integer id) throws Exception;

}
