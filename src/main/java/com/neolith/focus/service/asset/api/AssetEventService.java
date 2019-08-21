package com.neolith.focus.service.asset.api;

import com.neolith.focus.dto.asset.AssetEventDTO;
import com.neolith.focus.dto.asset.AssetEventTypeAssetDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface AssetEventService {

    DataTablesOutput<AssetEventDTO> findByEventTypeAndAsset(FocusDataTablesInput input, Integer assetId, Integer assetEventTypeId) throws Exception;

    DataTablesOutput<AssetEventDTO> findAssetEventHistory(Integer id);

    List<AssetEventDTO> findByAssetId(Integer id) throws Exception;

    List<AssetEventTypeAssetDTO> findAssetEventTypeAssetByAssetId(Integer assetId) throws Exception;

}
