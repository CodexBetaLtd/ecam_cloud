package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface MiscellaneousExpenseTypeDao extends FocusDataTableRepository<MiscellaneousExpenseType, Integer>{


}
