package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.workorder.WorkOrderLog;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderLogDao extends FocusDataTableRepository<WorkOrderLog, Integer> {

}
