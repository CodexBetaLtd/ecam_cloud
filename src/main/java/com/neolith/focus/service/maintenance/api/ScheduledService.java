package com.neolith.focus.service.maintenance.api;

import java.util.Date;
import java.util.Set;

import com.neolith.focus.constants.SMTriggerType;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;

public interface ScheduledService {

	void triggerTimeSchedules();

	void notifyAssetTrigger(Asset asset, SMTriggerType triggerType);

	void setNextCalendarEvent(ScheduledMaintenanceTrigger smt, Date startDate);

	boolean isAllSMWorkOrdersClosed(Set<ScheduledMaintenanceTask> smTasks);

}
