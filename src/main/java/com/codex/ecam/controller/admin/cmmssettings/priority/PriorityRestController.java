package com.codex.ecam.controller.admin.cmmssettings.priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.PriorityService;

import javax.validation.Valid;

@RestController
@RequestMapping(PriorityRestController.REQUEST_MAPPING_URL)
public class PriorityRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/priority";

	@Autowired
	private PriorityService priorityService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<PriorityDTO> getMeterReadingUnits(@Valid FocusDataTablesInput input, @RequestParam(name = "bizId")Integer bizId){
		try {
			return priorityService.findAllByBusiness(input, bizId);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<PriorityDTO>();
		}
	}

}
