package com.codex.ecam.service.report.stockadjustment.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.data.StockAdjustmentRepDTO;
import com.codex.ecam.dto.report.filter.StockAdjustmentFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface StockAdjustmentReportService {

	
	DataTablesOutput<StockAdjustmentRepDTO> searchDetail(FocusDataTablesInput input, StockAdjustmentFilterDTO filter) throws Exception;

	void print(StockAdjustmentFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception; 
	
}