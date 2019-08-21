package com.codex.ecam.dao.maintenance;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.workorder.WorkOrderTask;
import com.codex.ecam.repository.FocusDataTableRepository;


@Repository
public interface WorkOrderTaskDao extends FocusDataTableRepository<WorkOrderTask, Integer> {


}
