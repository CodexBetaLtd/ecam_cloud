package com.neolith.focus.service.inventory.api;

import com.neolith.focus.constants.inventory.StockAdjustmentStatus;
import com.neolith.focus.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.neolith.focus.dto.inventory.stockAdjuestment.StockAdjustmentFilterDTO;
import com.neolith.focus.dto.inventory.stockAdjuestment.StockAdjustmentRepDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.inventory.StockAdjustmentResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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
