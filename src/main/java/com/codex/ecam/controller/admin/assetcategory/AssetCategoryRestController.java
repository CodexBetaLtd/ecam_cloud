package com.codex.ecam.controller.admin.assetcategory;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetCategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(AssetCategoryRestController.REQUEST_MAPPING_URL)
public class AssetCategoryRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/assetCategory";

	@Autowired
	private AssetCategoryService assetCategoryService;
	
	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<AssetCategoryDTO> getAssetCategory(@Valid FocusDataTablesInput input) throws Exception {
        return assetCategoryService.findAll(input);
    }
	
	@RequestMapping(value = "/tabledatabytype", method = RequestMethod.GET)
	public DataTablesOutput<AssetCategoryDTO> getAssetCategoryByType(@Valid FocusDataTablesInput input, AssetCategoryType type) throws Exception {
		return assetCategoryService.findByAssetCategoyType(input, type);
	}
	
	@RequestMapping(value = "/tabledatabytypeparentid", method = RequestMethod.GET)
    public DataTablesOutput<AssetCategoryDTO> getAssetCategoryByType(@Valid FocusDataTablesInput input, Integer id) throws Exception {
        return assetCategoryService.findByAssetCategoyTypeById(input, id);
    }

}
