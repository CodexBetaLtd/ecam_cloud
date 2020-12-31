package com.codex.ecam.controller.report.stock.stockage;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.StockAgeRepDTO;
import com.codex.ecam.dto.report.filter.StockAgeFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.stock.stockage.api.StockAgeReportService;


@RestController
@RequestMapping(StockAgeReportRestController.REQUEST_MAPPING_URL)
public class StockAgeReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/stockage";

	@Autowired
	private StockAgeReportService stockAgeReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<StockAgeRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid StockAgeFilterDTO filter) {
		try {
			return stockAgeReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}