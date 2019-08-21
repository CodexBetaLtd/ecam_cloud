package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.MaintenanceTypeDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.maintenance.MaintenanceType;

public class MaintenanceTypeMapper extends GenericMapper<MaintenanceType, MaintenanceTypeDTO> {

	private static MaintenanceTypeMapper instance = null;

	private MaintenanceTypeMapper() {
	}

	public static MaintenanceTypeMapper getInstance() {
		if (instance == null) {
			instance = new MaintenanceTypeMapper();
		}
		return instance;
	}

	@Override
	public MaintenanceTypeDTO domainToDto(MaintenanceType domain) throws Exception {
		MaintenanceTypeDTO dto = new MaintenanceTypeDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setColor(domain.getColor());

		if ( domain.getBusiness() != null )  {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}


	@Override
	public void dtoToDomain(MaintenanceTypeDTO dto, MaintenanceType domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setColor(dto.getColor());

		setCommanDomainFields(dto, domain);

	}

	@Override
	public MaintenanceTypeDTO domainToDtoForDataTable(MaintenanceType domain) throws Exception {
		MaintenanceTypeDTO dto = new MaintenanceTypeDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setColor(domain.getColor()); 
		if ( domain.getBusiness() != null )  {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}


}
