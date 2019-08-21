package com.neolith.focus.service.maintenance.api;

import java.util.List;

import com.neolith.focus.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.neolith.focus.dto.maintenance.workOrder.MiscellaneousExpenseDTO;


public interface MiscellaneousExpenseService {

	List<MiscellaneousExpenseTypeDTO> findAll();

	MiscellaneousExpenseDTO findById(Integer id);


}
