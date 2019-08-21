package com.codex.ecam.service.maintenance.api;

import java.util.List;

import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.dto.maintenance.workOrder.MiscellaneousExpenseDTO;


public interface MiscellaneousExpenseService {

	List<MiscellaneousExpenseTypeDTO> findAll();

	MiscellaneousExpenseDTO findById(Integer id);


}
