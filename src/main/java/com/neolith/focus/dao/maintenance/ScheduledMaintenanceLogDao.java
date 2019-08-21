package com.neolith.focus.dao.maintenance;
 
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceLog;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface ScheduledMaintenanceLogDao extends FocusDataTableRepository<ScheduledMaintenanceLog, Integer> {

}
