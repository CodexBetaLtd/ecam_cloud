package com.codex.ecam.service.report.aodreturn.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.data.AODReturnRepDTO;
import com.codex.ecam.dto.report.filter.AODReturnFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface AODReturnReportService {

	
	DataTablesOutput<AODReturnRepDTO> searchDetail(FocusDataTablesInput input, AODReturnFilterDTO filter) throws Exception;

	void print(AODReturnFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception; 
	
}