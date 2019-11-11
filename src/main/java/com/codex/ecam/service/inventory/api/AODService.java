package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.dto.inventory.aod.AODDTO;
import com.codex.ecam.dto.inventory.aod.AODFilterDTO;
import com.codex.ecam.dto.inventory.aod.AODItemDTO;
import com.codex.ecam.dto.inventory.aod.AODRepDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.AODResult;

import java.math.BigDecimal;
import java.util.List;


public interface AODService {

    AODResult newAOD();

    AODResult save(AODDTO aodDTO) throws Exception;

    AODResult update(AODDTO aodDTO) throws Exception;

    AODResult delete(Integer id) throws Exception;

    AODResult findById(Integer id) throws Exception;

    AODResult statusChange(Integer id, AODStatus status);
    AODResult generateAodFromMrn(String ids, Integer mrnId);


    AODRepDTO findAODRepById(Integer id) throws Exception;

    BigDecimal getAODItemRemainQty(Integer aodItemId);

    List<AODRepDTO> findAll(AODFilterDTO aodFilterDTO) throws Exception;

    List<AODDTO> findAll();


    DataTablesOutput<AODDTO> findAll(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<AODItemDTO> findAll(FocusDataTablesInput input, Integer id);

}
