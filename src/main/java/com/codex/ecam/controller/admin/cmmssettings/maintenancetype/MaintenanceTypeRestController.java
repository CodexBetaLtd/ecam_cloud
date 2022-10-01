package com.codex.ecam.controller.admin.cmmssettings.maintenancetype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;

import javax.validation.Valid;

@RestController
@RequestMapping(MaintenanceTypeRestController.REQUEST_MAPPING_URL)
public class MaintenanceTypeRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/maintenance-type";

	@Autowired
	private MaintenanceTypeService maintenanceTypeService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<MaintenanceTypeDTO> getMeterReadingUnits(@Valid FocusDataTablesInput input, @RequestParam(name = "bizId")Integer bizId){
		try {
			return maintenanceTypeService.findByBusiness(input, bizId);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<MaintenanceTypeDTO>();
		}
	}

}
