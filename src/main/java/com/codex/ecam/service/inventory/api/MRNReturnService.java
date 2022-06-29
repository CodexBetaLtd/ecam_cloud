package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.MRNReturnStatus;
import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNReturnResult;
import com.codex.ecam.result.purchasing.PurchaseOrderResult;


public interface MRNReturnService {

	MRNReturnResult newMRN();

	String getNextCode(Integer businessId);

	MRNReturnResult save(MRNReturnDTO mrnReturnDTO) throws Exception;

	MRNReturnResult update(MRNReturnDTO mrnReturnDTO) throws Exception;

	MRNReturnResult delete(Integer id) throws Exception;

	MRNReturnResult findById(Integer id) throws Exception;

	MRNReturnResult statusChange(Integer id, MRNReturnStatus status);

	DataTablesOutput<MRNReturnDTO> findAll(FocusDataTablesInput input) throws Exception;

	MRNReturnResult deleteMultiple(Integer[] ids) throws Exception;


}
