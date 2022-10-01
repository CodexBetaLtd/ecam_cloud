package com.codex.ecam.mappers.asset;

import com.codex.ecam.dto.asset.AssetEventTypeAssetDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetEventTypeAsset;

public class AssetEventTypeAssetMapper extends GenericMapper<AssetEventTypeAsset, AssetEventTypeAssetDTO> {

    private static AssetEventTypeAssetMapper instance = null;

    private AssetEventTypeAssetMapper() {
    }

    public static AssetEventTypeAssetMapper getInstance() {
        if (instance == null) {
            instance = new AssetEventTypeAssetMapper();
        }
        return instance;
    }

    @Override
    public AssetEventTypeAssetDTO domainToDto(AssetEventTypeAsset domain) throws Exception {

        AssetEventTypeAssetDTO dto = new AssetEventTypeAssetDTO();

        dto.setId(domain.getId());
        dto.setAssetId(domain.getAsset().getId());
        dto.setAssetEventTypeId(domain.getAssetEventType().getId());
        dto.setAssetEventTypeName(domain.getAssetEventType().getName());

        if (domain.getLatestAssetEvent() != null) {
            dto.setLatestAssetEventId(domain.getLatestAssetEvent().getId());
            dto.setLatestEventOccurDate(domain.getLatestAssetEvent().getOccuredDate().getTime());
        }        
        
        dto.setVersion(domain.getVersion());
         
        return dto;
    }

    @Override
    public void dtoToDomain(AssetEventTypeAssetDTO dto, AssetEventTypeAsset domain) throws Exception {
        domain.setId(dto.getId());
        domain.setVersion(dto.getVersion());

        domain.setIsDeleted(dto.getIsDeleted());
    }

    @Override
    public AssetEventTypeAssetDTO domainToDtoForDataTable(AssetEventTypeAsset domain) throws Exception {
    	AssetEventTypeAssetDTO dto = new AssetEventTypeAssetDTO();

        dto.setId(domain.getId());
        dto.setAssetId(domain.getAsset().getId());
        dto.setAssetEventTypeId(domain.getAssetEventType().getId());
        dto.setAssetEventTypeName(domain.getAssetEventType().getName());
         
        return dto;
    }
}
