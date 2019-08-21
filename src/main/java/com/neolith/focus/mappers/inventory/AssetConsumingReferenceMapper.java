package com.neolith.focus.mappers.inventory;

import com.neolith.focus.dto.inventory.AssetConsumingReferenceDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetConsumingReference;

public class AssetConsumingReferenceMapper extends GenericMapper<AssetConsumingReference, AssetConsumingReferenceDTO> {

    private static AssetConsumingReferenceMapper instance = null;

    private AssetConsumingReferenceMapper() {
    }

    public static AssetConsumingReferenceMapper getInstance() {
        if (instance == null) {
            instance = new AssetConsumingReferenceMapper();
        }
        return instance;
    }

	@Override
	public AssetConsumingReferenceDTO domainToDto(AssetConsumingReference domain) throws Exception {
		AssetConsumingReferenceDTO dto = new AssetConsumingReferenceDTO();
		dto.setId(domain.getId());
		dto.setMaxConsumption(domain.getMaxConsumption());
		
		if ( domain.getAsset() != null ){
			dto.setAssetId(domain.getAsset().getId());
			dto.setAssetName(domain.getAsset().getName());
		}
		
		if ( domain.getPart() != null ) {
			dto.setPartId(domain.getPart().getId());
			dto.setPartName(domain.getPart().getName());
		}
		
		if ( domain.getBomGroupAsset() != null ) {
			dto.setAssetName(domain.getBomGroupAsset().getPart().getName());
			dto.setBomGroupPartId(domain.getBomGroupAsset().getId());
			dto.setBomGroupName(domain.getBomGroupAsset().getBomGroup().getName());
		}
		
		dto.setIsDeleted(domain.getIsDeleted());
		dto.setVersion(domain.getVersion());
		return dto;
	}

	@Override
	public void dtoToDomain(AssetConsumingReferenceDTO dto, AssetConsumingReference domain) throws Exception {
		domain.setId(dto.getId());
		domain.setMaxConsumption(dto.getMaxConsumption());		
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
	}

    @Override
    public AssetConsumingReferenceDTO domainToDtoForDataTable(AssetConsumingReference domain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
