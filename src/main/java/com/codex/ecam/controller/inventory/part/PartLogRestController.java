package com.codex.ecam.controller.inventory.part;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.biz.part.PartLogDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.log.api.PartLogService;

@RestController
@RequestMapping(PartLogRestController.REQUEST_MAPPING)
public class PartLogRestController {

	public static final String REQUEST_MAPPING = "restapi/partlog";

	@Autowired
	private PartLogService partLogService;

	@RequestMapping(value = "/table-data-by-part", method = RequestMethod.GET)
	public DataTablesOutput<PartLogDTO> findAll(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
		return partLogService.findAllByPartId(dataTablesInput, id);
	}
}
