package com.codex.ecam.controller.biz.business;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.biz.api.BusinessService;

@RestController
@RequestMapping(BusinessRestController.REQUEST_MAPPING_URL)
public class BusinessRestController {

	public static final String REQUEST_MAPPING_URL = "/restapi/business";

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<BusinessDTO> findAllBusinesses(@Valid FocusDataTablesInput input) {
		try {
			return businessService.findAllByLevel(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getActualBusinesses", method = RequestMethod.GET)
	public DataTablesOutput<BusinessDTO> findAllActualBusinesses(@Valid FocusDataTablesInput input) {
		try {
			return businessService.findActualBusinesses(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
