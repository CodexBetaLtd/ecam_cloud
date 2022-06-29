package com.codex.ecam.controller.report.issuenote;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aod.api.AODReportService;


@RestController
@RequestMapping(IssueNoteReportRestController.REQUEST_MAPPING_URL)
public class IssueNoteReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/issuenote";

	@Autowired
	private AODReportService aodReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<AODRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid AODFilterDTO filter) {
		try {
			return aodReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}