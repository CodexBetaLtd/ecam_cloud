package com.codex.ecam.controller.admin.cmmssettings.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.CountryService;

import javax.validation.Valid;

@RestController
@RequestMapping(CountryRestController.REQUEST_MAPPING_URL)
public class CountryRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/country";

	@Autowired
	private CountryService countryService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<CountryDTO> getCountry(@Valid FocusDataTablesInput input){
		try {
			return countryService.findAll(input);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<CountryDTO>();
		}
	}

}
