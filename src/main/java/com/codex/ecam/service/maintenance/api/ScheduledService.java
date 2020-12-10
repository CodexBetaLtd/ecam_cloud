package com.codex.ecam.service.maintenance.api;

import java.util.Date;
import java.util.Set;

import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;

public interface ScheduledService {

	void triggerTimeSchedules();

	void notifyAssetTrigger(Asset asset, SMTriggerType triggerType);

	void setNextCalendarEvent(ScheduledMaintenanceTrigger smt, Date startDate);
	
	 void setNextMeterReading(ScheduledMaintenanceTrigger smt);

	boolean isAllSMWorkOrdersClosed(Set<ScheduledMaintenanceTask> smTasks);
	
	void createWorkOrderFromTriggerType(ScheduledMaintenanceTrigger smt, SMTriggerType triggerType);

}
