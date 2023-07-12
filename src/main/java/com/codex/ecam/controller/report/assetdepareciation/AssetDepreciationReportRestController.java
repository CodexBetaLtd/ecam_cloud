package com.codex.ecam.controller.report.assetdepareciation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(AssetDepreciationReportRestController.REQUEST_MAPPING_URL)
public class AssetDepreciationReportRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/report/asset-depreciation";

}