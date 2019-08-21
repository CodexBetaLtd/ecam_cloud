package com.neolith.focus.controller.asset;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neolith.focus.dto.asset.AssetLogDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.log.api.AssetLogService;

@RestController
@RequestMapping(AssetLogRestController.REQUEST_MAPPING)
public class AssetLogRestController {

	public static final String REQUEST_MAPPING = "restapi/assetlog";
	
	@Autowired
	private AssetLogService assetLogService;
	
    @RequestMapping(value = "/tableDataByAsset", method = RequestMethod.GET)
    public DataTablesOutput<AssetLogDTO> findAll(@Valid FocusDataTablesInput dataTablesInput, @Valid Integer id) throws Exception {
        return assetLogService.findAllByAssetId(dataTablesInput, id);
    }
}
