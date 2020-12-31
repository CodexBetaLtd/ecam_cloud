package com.codex.ecam.mappers.app;

import com.codex.ecam.dto.app.RelatedAppDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.app.RelatedApp;

public class RelatedAppMapper extends GenericMapper<RelatedApp, RelatedAppDTO> {

	private static RelatedAppMapper instance = null;

	private RelatedAppMapper(){
	}

	public static RelatedAppMapper getInstance() {
		if (instance == null) {
			instance = new RelatedAppMapper();
		}
		return instance;
	}

	@Override
	public RelatedAppDTO domainToDto(RelatedApp domain) throws Exception {
		RelatedAppDTO dto = new RelatedAppDTO();
		dto.setId(domain.getId());
		dto.setAppId(domain.getApp().getId());
		dto.setRelatedAppId(domain.getRelatedApp().getId());
		dto.setRelatedAppName(domain.getRelatedApp().getName());

		setCommanDTOFields(dto, domain);
		return dto;
	}

	@Override
	public RelatedAppDTO domainToDtoForDataTable(RelatedApp domain) throws Exception {
		RelatedAppDTO dto = new RelatedAppDTO();
		dto.setId(domain.getId());
		dto.setAppId(domain.getApp().getId());
		dto.setRelatedAppId(domain.getRelatedApp().getId());
		dto.setRelatedAppName(domain.getRelatedApp().getName());

		return dto;
	}

	@Override
	public void dtoToDomain(RelatedAppDTO dto, RelatedApp domain) throws Exception {
		domain.setId(dto.getId());

		setCommanDomainFields(dto, domain);
	}

}
