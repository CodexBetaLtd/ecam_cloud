package com.codex.ecam.controller.biz.business;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
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

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<BusinessDTO> findAllBusinesses(@Valid FocusDataTablesInput input) {
		try {
			return businessService.findAllByLevel(input);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/actual-business/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<BusinessDTO> findAllActualBusinesses(@Valid FocusDataTablesInput input) {
		try {
			return businessService.findActualBusinesses(input);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new DataTablesOutput<BusinessDTO> ();
	}

}
