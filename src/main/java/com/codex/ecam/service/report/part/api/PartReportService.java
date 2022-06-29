package com.codex.ecam.service.report.part.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.data.PartRepDTO;
import com.codex.ecam.dto.report.filter.PartFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface PartReportService {

	
	DataTablesOutput<PartRepDTO> searchDetail(FocusDataTablesInput input, PartFilterDTO filter) throws Exception;

	void print(PartFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception; 
	
}