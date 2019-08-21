package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.AssetEventTypeDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetEventType;

public class AssetEventTypeMapper extends GenericMapper<AssetEventType, AssetEventTypeDTO> {

	private static AssetEventTypeMapper instance = null;

	private AssetEventTypeMapper() {
	}

	public static AssetEventTypeMapper getInstance() {
		if (instance == null) {
			instance = new AssetEventTypeMapper();
		}
		return instance;
	}

	@Override
	public AssetEventTypeDTO domainToDto(AssetEventType domain) throws Exception {
		AssetEventTypeDTO dto = new AssetEventTypeDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(AssetEventTypeDTO dto, AssetEventType domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setDescription(dto.getDescription());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public AssetEventTypeDTO domainToDtoForDataTable(AssetEventType domain) throws Exception {
		AssetEventTypeDTO dto = new AssetEventTypeDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());

		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
