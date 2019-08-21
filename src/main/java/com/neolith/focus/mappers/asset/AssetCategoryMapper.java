package com.neolith.focus.mappers.asset;


import com.neolith.focus.dto.asset.AssetCategoryDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetCategory;

public class AssetCategoryMapper extends GenericMapper<AssetCategory, AssetCategoryDTO> {

	private static AssetCategoryMapper instance = null;
	
	private AssetCategoryMapper(){
	}
	
	public static AssetCategoryMapper getInstance() {
		if (instance == null) {
			instance = new AssetCategoryMapper();
		}
		return instance;
	}

	@Override
	public AssetCategoryDTO domainToDto(AssetCategory domain) throws Exception {
		AssetCategoryDTO dto = new AssetCategoryDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setOverideRule(domain.getOverideRule());
		
		if ( domain.getAssetCategoryType() != null ) {
			dto.setType(domain.getAssetCategoryType() );
		}
		
		if ( domain.getParentAssetCategory() != null ) {
			dto.setParentId(domain.getParentAssetCategory().getId());
			dto.setParentName(domain.getParentAssetCategory().getName());
		}
		if ( domain.getBusiness()!= null ) {
			dto.setParentId(domain.getBusiness().getId());
			dto.setParentName(domain.getBusiness().getName());
		}
		dto.setVersion(domain.getVersion());
		return dto;
	}

	@Override
	public void dtoToDomain(AssetCategoryDTO dto, AssetCategory domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setOverideRule(dto.getOverideRule());
		domain.setAssetCategoryType(dto.getType());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
	}

    @Override
    public AssetCategoryDTO domainToDtoForDataTable(AssetCategory domain) throws Exception {
		AssetCategoryDTO dto = new AssetCategoryDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		if ( domain.getParentAssetCategory() != null ) {
			dto.setParentName(domain.getParentAssetCategory().getName());
		}
		return dto;
    }
}
