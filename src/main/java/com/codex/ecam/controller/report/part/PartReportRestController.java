package com.codex.ecam.controller.report.part;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.PartRepDTO;
import com.codex.ecam.dto.report.filter.PartFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.part.api.PartReportService;


@RestController
@RequestMapping(PartReportRestController.REQUEST_MAPPING_URL)
public class PartReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/part";

	@Autowired
	private PartReportService partReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<PartRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid PartFilterDTO filter) {
		try {
			return partReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}