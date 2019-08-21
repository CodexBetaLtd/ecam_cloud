package com.codex.ecam.dao.maintenance;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.workorder.WorkOrderLog;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface WorkOrderLogDao extends FocusDataTableRepository<WorkOrderLog, Integer> {

}
