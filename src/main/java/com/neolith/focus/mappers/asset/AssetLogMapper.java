package com.neolith.focus.mappers.asset;

import com.neolith.focus.dto.asset.AssetLogDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetLog;

public class AssetLogMapper extends GenericMapper<AssetLog, AssetLogDTO> {

	private static AssetLogMapper instance = null;
	
	private AssetLogMapper() {}
	
	public static AssetLogMapper getInstance(){
		if (instance == null) {
			instance = new AssetLogMapper();
		}
		return instance;
	}

	@Override
	public AssetLogDTO domainToDto(AssetLog domain) throws Exception {
		AssetLogDTO dto = new AssetLogDTO();
		dto.setId(domain.getId());
		dto.setNotes(domain.getNotes());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setUserName(domain.getCreatedUser().getFullName());
		
		if(domain.getAsset() != null){
			dto.setAssetId(domain.getAsset().getId());
			dto.setAssetName(domain.getAsset().getName());
		}
		
		if(domain.getLogType() != null){
			dto.setLogType(domain.getLogType());
		}
		
		setCommanDTOFields(dto, domain);
		return dto;
	}

	@Override
	public AssetLogDTO domainToDtoForDataTable(AssetLog domain) throws Exception {
		return domainToDto(domain);
	}

	@Override
	public void dtoToDomain(AssetLogDTO dto, AssetLog domain) throws Exception {
		domain.setId(dto.getId());
		domain.setLogType(dto.getLogType());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setNotes(dto.getNotes());
		setCommanDomainFields(dto, domain);
	}

}
