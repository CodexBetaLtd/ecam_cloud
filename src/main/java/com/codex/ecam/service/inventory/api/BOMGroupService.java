package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.inventory.bom.BOMGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.BOMGroupResult;

public interface BOMGroupService {

	DataTablesOutput<BOMGroupDTO> findAll(FocusDataTablesInput input) throws Exception;

	BOMGroupDTO findById(Integer id) throws Exception;

	BOMGroupResult delete(Integer id);

	BOMGroupResult update(BOMGroupDTO bomGroup);

	BOMGroupResult save(BOMGroupDTO bomGroup);

	DataTablesOutput<BOMGroupDTO> findBOMGroupsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

	BOMGroupResult deleteMultiple(Integer[] ids) throws Exception;

}
