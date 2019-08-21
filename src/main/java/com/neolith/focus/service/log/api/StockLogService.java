package com.neolith.focus.service.log.api;

import com.neolith.focus.dto.inventory.stock.StockLedgerDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface StockLogService {

	DataTablesOutput<StockLedgerDTO> findAllByPartId(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<StockLedgerDTO> findAllByStockId(FocusDataTablesInput input, Integer stockId) throws Exception;

    List<StockLedgerDTO> findAllStockByPartId(Integer partId) throws Exception;

}
