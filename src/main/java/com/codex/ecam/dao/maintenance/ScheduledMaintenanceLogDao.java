package com.codex.ecam.dao.maintenance;
 
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceLog;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface ScheduledMaintenanceLogDao extends FocusDataTableRepository<ScheduledMaintenanceLog, Integer> {

}
