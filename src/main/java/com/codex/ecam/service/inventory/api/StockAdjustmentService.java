package com.codex.ecam.service.inventory.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentFilterDTO;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentRepDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.StockAdjustmentResult;

import java.util.List;

public interface StockAdjustmentService {

	DataTablesOutput<StockAdjustmentDTO> findAll(FocusDataTablesInput input) throws Exception;
	
    StockAdjustmentDTO newStockAdjustment();

    StockAdjustmentDTO findById(Integer id) throws Exception;
    
    StockAdjustmentResult statusChange(Integer id, StockAdjustmentStatus stockAdjustmentStatus);

    StockAdjustmentResult delete(Integer id);

    StockAdjustmentResult update(StockAdjustmentDTO dto);

    StockAdjustmentResult save(StockAdjustmentDTO dto);

    List<StockAdjustmentRepDTO> findAll(StockAdjustmentFilterDTO filterDTO);

    StockAdjustmentRepDTO findRepDTOById(Integer id);
}
