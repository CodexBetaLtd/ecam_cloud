package com.codex.ecam.controller.report.aodreturn;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.aodReturn.AODReturnRepDTO;
import com.codex.ecam.dto.report.filter.AODReturnFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aodreturn.api.AODReturnReportService;


@RestController
@RequestMapping(AODReturnReportRestController.REQUEST_MAPPING_URL)
public class AODReturnReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/aodreturn";

	@Autowired
	private AODReturnReportService aodReturnReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<AODReturnRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid AODReturnFilterDTO filter) {
		try {
			return aodReturnReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}