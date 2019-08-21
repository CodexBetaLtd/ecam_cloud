package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.AssetBrandDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.AssetBrand;

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
