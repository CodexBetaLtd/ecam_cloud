package com.neolith.focus.mappers.asset;

import com.neolith.focus.dto.asset.AssetEventDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetEvent;

import java.util.Date;

public class AssetEventMapper extends GenericMapper<AssetEvent, AssetEventDTO> {

    private static AssetEventMapper instance = null;

    private AssetEventMapper() {
    }

    public static AssetEventMapper getInstance() {
        if (instance == null) {
            instance = new AssetEventMapper();
        }
        return instance;
    }

    @Override
    public AssetEventDTO domainToDto(AssetEvent domain) throws Exception {
    	AssetEventDTO dto = new AssetEventDTO();
        dto.setAssetEventId(domain.getId());
        dto.setAssetEventTypeId(domain.getAssetEventTypeAsset().getAssetEventType().getId());
        dto.setAssetEventTypeName(domain.getAssetEventTypeAsset().getAssetEventType().getName());
        dto.setAssetEventDescription(domain.getAdditionalDescription());
        dto.setAssetEventTypeAssetId(domain.getAssetEventTypeAsset().getId());
        dto.setAssetEventOccuredDate(domain.getOccuredDate().getTime());
        
        dto.setVersion(domain.getVersion());

        return dto;
    }

    @Override
    public void dtoToDomain(AssetEventDTO dto, AssetEvent domain) throws Exception {
        domain.setId(dto.getAssetEventId());
        domain.setVersion(dto.getVersion());
        domain.setOccuredDate(new Date(dto.getAssetEventOccuredDate()));
        domain.setIsDeleted(dto.getIsDeleted());
    }

    @Override
    public AssetEventDTO domainToDtoForDataTable(AssetEvent domain) throws Exception {
    	AssetEventDTO dto = new AssetEventDTO();
        dto.setAssetEventId(domain.getId());
        dto.setAssetEventTypeName(domain.getAssetEventTypeAsset().getAssetEventType().getName());
        dto.setAssetEventDescription(domain.getAdditionalDescription());
        dto.setAssetEventOccuredDate(domain.getOccuredDate().getTime());
        
        dto.setVersion(domain.getVersion());

        return dto;
    }
}
