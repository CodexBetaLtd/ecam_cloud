package com.neolith.focus.service.inventory.api;

import com.neolith.focus.constants.inventory.AODStatus;
import com.neolith.focus.dto.inventory.aod.AODDTO;
import com.neolith.focus.dto.inventory.aod.AODFilterDTO;
import com.neolith.focus.dto.inventory.aod.AODItemDTO;
import com.neolith.focus.dto.inventory.aod.AODRepDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.inventory.AODResult; 

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.math.BigDecimal;
import java.util.List;


public interface AODService {

    AODResult newAOD();

    AODResult save(AODDTO aodDTO) throws Exception;

    AODResult update(AODDTO aodDTO) throws Exception;

    AODResult delete(Integer id) throws Exception;

    AODResult findById(Integer id) throws Exception;

    AODResult statusChange(Integer id, AODStatus status);


    AODRepDTO findAODRepById(Integer id) throws Exception;

    BigDecimal getAODItemRemainQty(Integer aodItemId);

    List<AODRepDTO> findAll(AODFilterDTO aodFilterDTO) throws Exception;

    List<AODDTO> findAll();


    DataTablesOutput<AODDTO> findAll(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<AODItemDTO> findAll(FocusDataTablesInput input, Integer id);

}
