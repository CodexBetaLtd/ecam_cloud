package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.MiscellaneousExpenseTypeResult;


public interface MiscellaneousExpenseTypeService {

	DataTablesOutput<MiscellaneousExpenseTypeDTO> findAll(FocusDataTablesInput input) throws Exception;

	MiscellaneousExpenseTypeDTO findById(Integer id) throws Exception;

	MiscellaneousExpenseTypeResult delete(Integer id);

	MiscellaneousExpenseTypeResult save(MiscellaneousExpenseTypeDTO miscellaneousExpenseTypeDTO) throws Exception;

	List<MiscellaneousExpenseTypeDTO> findAll();


}
