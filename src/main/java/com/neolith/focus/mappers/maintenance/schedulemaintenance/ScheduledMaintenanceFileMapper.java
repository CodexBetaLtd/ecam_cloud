package com.neolith.focus.mappers.maintenance.schedulemaintenance;

import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceFileDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceFile;

public class ScheduledMaintenanceFileMapper extends GenericMapper<ScheduledMaintenanceFile, ScheduledMaintenanceFileDTO>{

	private static ScheduledMaintenanceFileMapper instance = null;

	private ScheduledMaintenanceFileMapper() {
	}

	public static ScheduledMaintenanceFileMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceFileMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenanceFileDTO domainToDto(ScheduledMaintenanceFile domain) throws Exception {
		ScheduledMaintenanceFileDTO dto = new ScheduledMaintenanceFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public ScheduledMaintenanceFileDTO domainToDtoForDataTable(ScheduledMaintenanceFile domain) throws Exception {
		ScheduledMaintenanceFileDTO dto = new ScheduledMaintenanceFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		return dto;
	}

	@Override
	public void dtoToDomain(ScheduledMaintenanceFileDTO dto, ScheduledMaintenanceFile domain) throws Exception {
		domain.setId(dto.getId());
		domain.setItemDescription(dto.getItemDescription());
		domain.setFileLocation(dto.getFileLocation());
		domain.setFileType(dto.getFileType());
		domain.setFileDate(dto.getFileDate());

		setCommanDomainFields(dto, domain);
	}

}
