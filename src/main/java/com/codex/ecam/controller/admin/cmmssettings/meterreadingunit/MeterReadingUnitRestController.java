package com.codex.ecam.controller.admin.cmmssettings.meterreadingunit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;

import javax.validation.Valid;

@RestController
@RequestMapping(MeterReadingUnitRestController.REQUEST_MAPPING_URL)
public class MeterReadingUnitRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/meterreading-units";

	@Autowired
	private MeterReadingUnitService meterReadingUnitService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<MeterReadingUnitDTO> getMeterReadingUnits(@Valid FocusDataTablesInput input){
		try {
			return meterReadingUnitService.findAll(input);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<MeterReadingUnitDTO>();
		}
	}

}
