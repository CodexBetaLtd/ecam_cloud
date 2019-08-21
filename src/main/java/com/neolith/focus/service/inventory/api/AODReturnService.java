package com.neolith.focus.service.inventory.api;

import com.neolith.focus.constants.inventory.AODReturnStatus;
import com.neolith.focus.dto.inventory.aodReturn.AODReturnDTO;
import com.neolith.focus.dto.inventory.aodReturn.AODReturnFilterDTO;
import com.neolith.focus.dto.inventory.aodReturn.AODReturnRepDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.inventory.AODReturnResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface AODReturnService {

    AODReturnResult newAODReturn();

    AODReturnResult save(AODReturnDTO part) throws Exception;

    AODReturnResult update(AODReturnDTO part) throws Exception;

    AODReturnResult delete(Integer id) throws Exception;

    AODReturnResult findById(Integer id) throws Exception;

    AODReturnResult returnByAODItem();

    AODReturnResult statusChange(Integer id, AODReturnStatus status) throws Exception;

    AODReturnRepDTO findAODReturnRepById(Integer id);

    List<AODReturnDTO> getUnFinalizedAODReturns();

    List<AODReturnDTO> findAll();

    List<AODReturnRepDTO> findAll(AODReturnFilterDTO aodReturnFilterDTO);

    DataTablesOutput<AODReturnDTO> findAll(FocusDataTablesInput input) throws Exception;

}
