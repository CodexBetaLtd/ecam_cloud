package com.codex.ecam.service.report.asset.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.dto.report.data.aod.AODRepItemDataDTO;
import com.codex.ecam.dto.report.data.asset.AssetRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.filter.AssetFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface AssetReportService {

	
	DataTablesOutput<AssetRepDTO> searchDetail(FocusDataTablesInput input, AssetFilterDTO filter) throws Exception;

	void print(AssetFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception; 
	
	void printDoc(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception; 
	
}