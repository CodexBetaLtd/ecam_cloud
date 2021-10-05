package com.codex.ecam.controller.admin.cmmssettings.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.biz.api.BusinessClassificationService;

import javax.validation.Valid;

@RestController
@RequestMapping(CurrencyRestController.REQUEST_MAPPING_URL)
public class CurrencyRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/currency";

	@Autowired
	private CurrencyService currencyService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<CurrencyDTO> getBusinessClassification(@Valid FocusDataTablesInput input){
		try {
			return currencyService.findAll(input);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<CurrencyDTO>();
		}
	}

}
