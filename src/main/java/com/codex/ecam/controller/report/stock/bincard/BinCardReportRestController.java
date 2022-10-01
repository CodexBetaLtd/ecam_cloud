package com.codex.ecam.controller.report.stock.bincard;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.BinCardRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.filter.BinCardFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aod.api.AODReportService;
import com.codex.ecam.service.report.stock.bincard.api.BinCardReportService;


@RestController
@RequestMapping(BinCardReportRestController.REQUEST_MAPPING_URL)
public class BinCardReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/bincard";

	@Autowired
	private BinCardReportService binCardReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<BinCardRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid BinCardFilterDTO filter) {
		try {
			return binCardReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}