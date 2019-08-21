package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.workorder.WorkOrderTask;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.stereotype.Repository;


@Repository
public interface WorkOrderTaskDao extends FocusDataTableRepository<WorkOrderTask, Integer> {


}
