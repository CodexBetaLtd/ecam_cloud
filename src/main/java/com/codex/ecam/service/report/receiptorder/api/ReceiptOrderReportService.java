package com.codex.ecam.service.report.receiptorder.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.data.receiptOrder.ReceiptOrderItemRepDTO;
import com.codex.ecam.dto.report.data.receiptOrder.ReceiptOrderRepDTO;
import com.codex.ecam.dto.report.filter.ReceiptOrderFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface ReceiptOrderReportService {

	
	DataTablesOutput<ReceiptOrderRepDTO> searchDetail(FocusDataTablesInput input, ReceiptOrderFilterDTO filter) throws Exception;

	void print(ReceiptOrderFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception;
	
	void printDoc(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception; 

	
}