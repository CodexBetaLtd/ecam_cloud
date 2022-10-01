package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.InventoryGroupResult;


public interface InventoryGroupService {

	InventoryGroupResult newInventoryGroup();

	InventoryGroupResult save(InventoryGroupDTO dto) throws Exception;

	InventoryGroupResult update(InventoryGroupDTO aodDTO) throws Exception;

	InventoryGroupResult delete(Integer id) throws Exception;

	InventoryGroupResult findById(Integer id) throws Exception;

	DataTablesOutput<InventoryGroupDTO> findAll(FocusDataTablesInput input) throws Exception;

	InventoryGroupResult deleteMultiple(Integer[] ids) throws Exception;


}
