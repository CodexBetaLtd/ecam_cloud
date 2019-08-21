package com.neolith.focus.mappers.asset;

import com.neolith.focus.dto.asset.AssetFileDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetFile;

public class AssetFileMapper extends GenericMapper<AssetFile, AssetFileDTO>{

	private static AssetFileMapper instance = null;

	private AssetFileMapper() {
	}

	public static AssetFileMapper getInstance() {
		if (instance == null) {
			instance = new AssetFileMapper();
		}
		return instance;
	}

	@Override
	public AssetFileDTO domainToDto(AssetFile domain) throws Exception {
		AssetFileDTO dto = new AssetFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public AssetFileDTO domainToDtoForDataTable(AssetFile domain) throws Exception {
		AssetFileDTO dto = new AssetFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		return dto;
	}

	@Override
	public void dtoToDomain(AssetFileDTO dto, AssetFile domain) throws Exception {
		domain.setId(dto.getId());
		domain.setItemDescription(dto.getItemDescription());
		domain.setFileLocation(dto.getFileLocation());
		domain.setFileType(dto.getFileType());
		domain.setFileDate(dto.getFileDate());

		setCommanDomainFields(dto, domain);
	}

}
