package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnFilterDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnRepDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.AODReturnResult;

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
