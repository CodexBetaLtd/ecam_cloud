package com.codex.ecam.service.report.stock.bincard.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.report.data.BinCardRepDTO;
import com.codex.ecam.dto.report.filter.BinCardFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface BinCardReportService {

	
	DataTablesOutput<BinCardRepDTO> searchDetail(FocusDataTablesInput input, BinCardFilterDTO filter) throws Exception;

	void print(BinCardFilterDTO filter, HttpServletResponse response, HttpServletRequest request, String type) throws Exception; 
	
}