package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.Priority;

public class PriorityMapper extends GenericMapper<Priority, PriorityDTO> {

	private static  PriorityMapper instance = null;

	private  PriorityMapper() {
	}

	public static PriorityMapper getInstance() {
		if (instance == null) {
			instance = new PriorityMapper();
		}
		return instance;
	}

	@Override
	public PriorityDTO domainToDto(Priority domain) throws Exception {
		PriorityDTO dto = new PriorityDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setColor(domain.getColor());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(PriorityDTO dto, Priority domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setColor(dto.getColor());

		setCommanDomainFields(dto, domain);

	}

	@Override
	public PriorityDTO domainToDtoForDataTable(Priority domain) throws Exception {
		PriorityDTO dto = new PriorityDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setColor(domain.getColor());
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
