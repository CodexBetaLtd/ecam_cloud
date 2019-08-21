package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.AssetModel;

public class AssetModelMapper extends GenericMapper<AssetModel, AssetModelDTO>{

	private static AssetModelMapper instance = null;

	public AssetModelMapper() {
	}

	public static AssetModelMapper getInstance(){
		if (instance == null) {
			instance = new AssetModelMapper();
		}
		return instance;
	}

	@Override
	public AssetModelDTO domainToDto(AssetModel domain) throws Exception {
		AssetModelDTO dto = new AssetModelDTO();
		dto.setModelId(domain.getId());
		dto.setModelName(domain.getModelName());
		if(domain.getAssetBrand() != null ){
			dto.setBrandId(domain.getAssetBrand().getId());
			dto.setBrandName(domain.getAssetBrand().getBrandName());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public AssetModelDTO domainToDtoForDataTable(AssetModel domain) throws Exception {
		AssetModelDTO dto = new AssetModelDTO();
		dto.setModelId(domain.getId());
		dto.setModelName(domain.getModelName());
		if(domain.getAssetBrand() != null ){
			dto.setBrandId(domain.getAssetBrand().getId());
			dto.setBrandName(domain.getAssetBrand().getBrandName());
		}
		return dto;
	}

	@Override
	public void dtoToDomain(AssetModelDTO dto, AssetModel domain) throws Exception {
		domain.setId(dto.getModelId());
		domain.setModelName(dto.getModelName());

		setCommanDomainFields(dto, domain);
	}

}
