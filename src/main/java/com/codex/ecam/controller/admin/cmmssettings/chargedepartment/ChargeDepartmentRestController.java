package com.codex.ecam.controller.admin.cmmssettings.chargedepartment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;

import javax.validation.Valid;

@RestController
@RequestMapping(ChargeDepartmentRestController.REQUEST_MAPPING_URL)
public class ChargeDepartmentRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/charge-department";

	@Autowired
	private ChargeDepartmentService chargeDepartmentService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<ChargeDepartmentDTO> getAll(@Valid FocusDataTablesInput input){
		try {
			return chargeDepartmentService.findAll(input);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<ChargeDepartmentDTO>();
		}
	}

}
