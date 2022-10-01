package com.codex.ecam.service.report.stock.stockage.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.report.data.StockAgeRepDTO;
import com.codex.ecam.dto.report.filter.StockAgeFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface StockAgeReportService {

	
	DataTablesOutput<StockAgeRepDTO> searchDetail(FocusDataTablesInput input, StockAgeFilterDTO filter) throws Exception;

	void print(StockAgeFilterDTO filter, HttpServletResponse response, HttpServletRequest request, String type) throws Exception; 
	
}