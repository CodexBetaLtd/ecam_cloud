package com.neolith.focus.service.inventory.api;

import com.neolith.focus.dto.inventory.bom.BOMGroupDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.inventory.BOMGroupResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface BOMGroupService {

    DataTablesOutput<BOMGroupDTO> findAll(FocusDataTablesInput input) throws Exception;

    BOMGroupDTO findById(Integer id) throws Exception;

    BOMGroupResult delete(Integer id);

    BOMGroupResult update(BOMGroupDTO bomGroup);

    BOMGroupResult save(BOMGroupDTO bomGroup);

	DataTablesOutput<BOMGroupDTO> findBOMGroupsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

}
