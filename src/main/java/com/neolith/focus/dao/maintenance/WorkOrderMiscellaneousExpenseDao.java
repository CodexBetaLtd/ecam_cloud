package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderMiscellaneousExpenseDao extends DataTablesRepository<WorkOrderMiscellaneousExpense, Integer> {


}
