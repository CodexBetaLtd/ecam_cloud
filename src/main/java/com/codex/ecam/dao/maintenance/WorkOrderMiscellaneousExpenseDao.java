package com.codex.ecam.dao.maintenance;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;

@Repository
public interface WorkOrderMiscellaneousExpenseDao extends DataTablesRepository<WorkOrderMiscellaneousExpense, Integer> {


}
