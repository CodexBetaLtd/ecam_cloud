package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.maintenance.WorkOrderResult;

public interface ScheduledMaintenanceTriggerService {

	DataTablesOutput<ScheduledMaintenanceTriggerDTO> findUpcomingScheduleMaintenance(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<ScheduledMaintenanceTriggerDTO> findTasksBetweenDates(FocusDataTablesInput dataTablesInput, String from, String to) throws Exception;

	WorkOrder createWorkOrderFromManualTrigger(ScheduledMaintenanceTrigger trigger, WorkOrder wo) throws Exception;

	WorkOrder createWorkOrderFromTrigger(ScheduledMaintenanceTrigger smt) throws Exception;

	WorkOrderResult manualTrigger(String ids, Integer smId)throws Exception;


}
