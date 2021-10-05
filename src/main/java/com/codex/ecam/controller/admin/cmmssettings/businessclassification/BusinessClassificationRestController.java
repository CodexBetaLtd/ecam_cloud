package com.codex.ecam.controller.admin.cmmssettings.businessclassification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.biz.api.BusinessClassificationService;

import javax.validation.Valid;

@RestController
@RequestMapping(BusinessClassificationRestController.REQUEST_MAPPING_URL)
public class BusinessClassificationRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/business-classification";

	@Autowired
	private BusinessClassificationService businessClassificationService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<BusinessClassificationDTO> getBusinessClassification(@Valid FocusDataTablesInput input){
		try {
			return businessClassificationService.findAllDataTable(input);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<BusinessClassificationDTO>();
		}
	}

}
