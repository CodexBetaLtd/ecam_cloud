package com.codex.ecam.controller.admin.assetcategory;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetCategoryService;

@RestController
@RequestMapping(AssetCategoryRestController.REQUEST_MAPPING_URL)
public class AssetCategoryRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/assetCategory";

	private AssetCategoryService assetCategoryService;

	@Autowired
	public AssetCategoryRestController(AssetCategoryService assetCategoryService) {
		super();
		this.assetCategoryService = assetCategoryService;
	}

	@GetMapping(value = "/tabledata")
	public DataTablesOutput<AssetCategoryDTO> getAssetCategory(@Valid FocusDataTablesInput input, @RequestParam(name = "bizId", required = false) Integer bizId) throws Exception {
		return assetCategoryService.findAllByBusiness(input, bizId);
	}

	@GetMapping(value = "/tabledatabytype")
	public DataTablesOutput<AssetCategoryDTO> getAssetCategoryByType(@Valid FocusDataTablesInput input, AssetCategoryType type) throws Exception {
		return assetCategoryService.findByAssetCategoyType(input, type);
	}

	@GetMapping(value = "/tabledatabytypeparentid")
	public DataTablesOutput<AssetCategoryDTO> getAssetCategoryByType(@Valid FocusDataTablesInput input, Integer id) throws Exception {
		return assetCategoryService.findByAssetCategoyTypeById(input, id);
	}

}
