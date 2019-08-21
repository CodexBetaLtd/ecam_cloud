package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.AssetBrand;

public class AssetBrandMapper extends GenericMapper<AssetBrand, AssetBrandDTO> {

	private static AssetBrandMapper instance = null;

	public AssetBrandMapper() {}

	public static AssetBrandMapper getInstance(){
		if(instance == null){
			instance = new AssetBrandMapper();
		}
		return instance;
	}

	@Override
	public AssetBrandDTO domainToDto(AssetBrand domain) throws Exception {
		AssetBrandDTO dto = new AssetBrandDTO();
		dto.setBrandId(domain.getId());
		dto.setBrandName(domain.getBrandName());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public AssetBrandDTO domainToDtoForDataTable(AssetBrand domain) throws Exception {
		AssetBrandDTO dto = new AssetBrandDTO();
		dto.setBrandId(domain.getId());
		dto.setBrandName(domain.getBrandName());
		return dto;
	}

	@Override
	public void dtoToDomain(AssetBrandDTO dto, AssetBrand domain) throws Exception {
		domain.setId(dto.getBrandId());
		domain.setBrandName(dto.getBrandName());

		setCommanDomainFields(dto, domain);
	}

}
