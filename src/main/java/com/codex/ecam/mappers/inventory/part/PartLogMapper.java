package com.codex.ecam.mappers.inventory.part;

import com.codex.ecam.dto.biz.part.PartLogDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetLog;

public class PartLogMapper extends GenericMapper<AssetLog, PartLogDTO> {

	private static PartLogMapper instance = null;
	
	private PartLogMapper() {}
	
	public static PartLogMapper getInstance(){
		if (instance == null) {
			instance = new PartLogMapper();
		}
		return instance;
	}

	@Override
	public PartLogDTO domainToDto(AssetLog domain) throws Exception {
		PartLogDTO dto = new PartLogDTO();
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
	public PartLogDTO domainToDtoForDataTable(AssetLog domain) throws Exception {
		return domainToDto(domain);
	}

	@Override
	public void dtoToDomain(PartLogDTO dto, AssetLog domain) throws Exception {
		domain.setId(dto.getId());
		domain.setLogType(dto.getLogType());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setNotes(dto.getNotes());
		setCommanDomainFields(dto, domain);
	}

}
