package com.codex.ecam.service.report.assetdepreciation.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.assetdepreciation.AssetDepreciationFilterDTO;

public interface AssetDepreciationReportService {

	void print(AssetDepreciationFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception;

}