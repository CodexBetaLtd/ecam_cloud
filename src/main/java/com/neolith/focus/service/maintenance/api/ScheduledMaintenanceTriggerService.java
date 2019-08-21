package com.neolith.focus.service.maintenance.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.maintenance.WorkOrderResult; 

public interface ScheduledMaintenanceTriggerService {

	DataTablesOutput<ScheduledMaintenanceTriggerDTO> findUpcomingScheduleMaintenance(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<ScheduledMaintenanceTriggerDTO> findTasksBetweenDates(FocusDataTablesInput dataTablesInput, String from, String to) throws Exception;

	WorkOrder createWorkOrderFromManualTrigger(ScheduledMaintenanceTrigger trigger, WorkOrder wo) throws Exception;

	WorkOrder createWorkOrderFromTrigger(ScheduledMaintenanceTrigger smt) throws Exception;

	WorkOrderResult manualTrigger(String ids, Integer smId);


}
