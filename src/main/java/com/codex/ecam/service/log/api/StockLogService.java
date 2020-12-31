package com.codex.ecam.service.log.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.inventory.stock.StockLedgerDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

import java.util.List;

public interface StockLogService {

	DataTablesOutput<StockLedgerDTO> findAllByPartId(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<StockLedgerDTO> findAllByStockId(FocusDataTablesInput input, Integer stockId) throws Exception;

    List<StockLedgerDTO> findAllStockByPartId(Integer partId) throws Exception;

}
