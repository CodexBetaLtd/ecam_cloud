package com.codex.ecam.controller.report.asset;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.report.data.asset.AssetRepDTO;
import com.codex.ecam.dto.report.filter.AssetFilterDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.asset.api.AssetReportService;


@RestController
@RequestMapping(AssetReportRestController.REQUEST_MAPPING_URL)
public class AssetReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/asset";

	@Autowired
	private AssetReportService assetReportService;

	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public DataTablesOutput<AssetRepDTO> searchDetail(@Valid FocusDataTablesInput input, @Valid AssetFilterDTO filter) {
		try {
			return assetReportService.searchDetail(input, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}