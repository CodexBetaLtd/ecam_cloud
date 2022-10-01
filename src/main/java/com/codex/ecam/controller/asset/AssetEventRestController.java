package com.codex.ecam.controller.asset;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.asset.AssetEventDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetEventService;

import javax.validation.Valid;

@RestController
@RequestMapping(AssetEventRestController.REQUEST_MAPPING_URL)
public class AssetEventRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/assetevent";

    @Autowired
    private AssetEventService assetEventService;

    @RequestMapping(value = "/assetEventsByEventTypeAndAsset/{assetId}/{assetEventTypeId}", method = RequestMethod.GET)
    public DataTablesOutput<AssetEventDTO> assetEventsByEventType(@Valid FocusDataTablesInput input, @PathVariable("assetId") Integer assetId, @PathVariable("assetEventTypeId") Integer assetEventTypeId) {
        try {
            return assetEventService.findByEventTypeAndAsset(input, assetId, assetEventTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/getEventList/{assetId}", method = RequestMethod.GET)
    public DataTablesOutput<AssetEventDTO> getEventList(@PathVariable("assetId") Integer id) {
        try {
            return assetEventService.findAssetEventHistory(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
