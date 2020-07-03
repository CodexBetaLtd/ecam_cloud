package com.codex.ecam.service.report.purchaseorder.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface PurchaseorderReportService {

	
	DataTablesOutput<AODRepDTO> searchDetail(FocusDataTablesInput input, AODFilterDTO filter) throws Exception;

	void print(AODFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception; 
	
	void printDoc(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception; 
	
}