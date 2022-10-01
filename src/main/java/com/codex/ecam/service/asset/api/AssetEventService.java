package com.codex.ecam.service.asset.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.AssetEventDTO;
import com.codex.ecam.dto.asset.AssetEventTypeAssetDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

import java.util.List;

public interface AssetEventService {

    DataTablesOutput<AssetEventDTO> findByEventTypeAndAsset(FocusDataTablesInput input, Integer assetId, Integer assetEventTypeId) throws Exception;

    DataTablesOutput<AssetEventDTO> findAssetEventHistory(Integer id);

    List<AssetEventDTO> findByAssetId(Integer id) throws Exception;

    List<AssetEventTypeAssetDTO> findAssetEventTypeAssetByAssetId(Integer assetId) throws Exception;

}
