package com.codex.ecam.controller.report.receiptorder;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.receiptOrder.ReceiptOrderRepDTO;
import com.codex.ecam.dto.report.filter.ReceiptOrderFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.receiptorder.api.ReceiptOrderReportService;


@RestController
@RequestMapping(ReceiptOrderReportRestController.REQUEST_MAPPING_URL)
public class ReceiptOrderReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/receiptorder";

	@Autowired
	private ReceiptOrderReportService receiptOrderReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<ReceiptOrderRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid ReceiptOrderFilterDTO filter) {
		try {
			return receiptOrderReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}