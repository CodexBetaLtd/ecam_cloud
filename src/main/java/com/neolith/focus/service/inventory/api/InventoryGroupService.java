package com.neolith.focus.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.inventory.InventoryGroupResult;


public interface InventoryGroupService {

    InventoryGroupResult newInventoryGroup();

    InventoryGroupResult save(InventoryGroupDTO dto) throws Exception;

    InventoryGroupResult update(InventoryGroupDTO aodDTO) throws Exception;

    InventoryGroupResult delete(Integer id) throws Exception;

    InventoryGroupResult findById(Integer id) throws Exception;

    DataTablesOutput<InventoryGroupDTO> findAll(FocusDataTablesInput input) throws Exception;


}
