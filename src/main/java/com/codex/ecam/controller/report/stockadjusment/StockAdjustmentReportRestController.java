package com.codex.ecam.controller.report.stockadjusment;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.StockAdjustmentRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.filter.StockAdjustmentFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aod.api.AODReportService;
import com.codex.ecam.service.report.stockadjustment.api.StockAdjustmentReportService;


@RestController
@RequestMapping(StockAdjustmentReportRestController.REQUEST_MAPPING_URL)
public class StockAdjustmentReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/stockadjustment";

	@Autowired
	private StockAdjustmentReportService stockAdjustmentReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<StockAdjustmentRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid StockAdjustmentFilterDTO filter) {
		try {
			return stockAdjustmentReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}