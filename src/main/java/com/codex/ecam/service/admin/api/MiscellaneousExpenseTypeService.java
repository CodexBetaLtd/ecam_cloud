package com.codex.ecam.service.admin.api;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.MiscellaneousExpenseTypeResult;


public interface MiscellaneousExpenseTypeService {

	DataTablesOutput<MiscellaneousExpenseTypeDTO> findAll(FocusDataTablesInput input) throws Exception;

	MiscellaneousExpenseTypeDTO findById(Integer id) throws Exception;

	MiscellaneousExpenseTypeResult delete(Integer id);

	MiscellaneousExpenseTypeResult save(MiscellaneousExpenseTypeDTO miscellaneousExpenseTypeDTO) throws Exception;

	List<MiscellaneousExpenseTypeDTO> findAll();

	MiscellaneousExpenseTypeResult deleteMultiple(Integer[] ids) throws Exception;


}
