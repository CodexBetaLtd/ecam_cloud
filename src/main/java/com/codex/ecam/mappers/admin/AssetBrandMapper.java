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
		final AssetBrandDTO dto = new AssetBrandDTO();
		dto.setBrandId(domain.getId());
		dto.setBrandName(domain.getBrandName());

		setBrandBusiness(domain, dto);
		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setBrandBusiness(AssetBrand domain, AssetBrandDTO dto) {
		if (domain.getBusiness() != null) {
			dto.setBrandBusinessId(domain.getBusiness().getId());
			dto.setBrandBusinessName(domain.getBusiness().getName());
		}
	}

	@Override
	public AssetBrandDTO domainToDtoForDataTable(AssetBrand domain) throws Exception {
		final AssetBrandDTO dto = new AssetBrandDTO();
		dto.setBrandId(domain.getId());
		dto.setBrandName(domain.getBrandName());
		setBrandBusiness(domain, dto);
		return dto;
	}

	@Override
	public void dtoToDomain(AssetBrandDTO dto, AssetBrand domain) throws Exception {
		domain.setId(dto.getBrandId());
		domain.setBrandName(dto.getBrandName());

		setCommanDomainFields(dto, domain);
	}

}
