package com.codex.ecam.mappers.maintenance.schedulemaintenance;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceLogDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceLog;

public class ScheduledMaintenanceLogMapper extends GenericMapper<ScheduledMaintenanceLog, ScheduledMaintenanceLogDTO> {

	private static ScheduledMaintenanceLogMapper instance = null;

	private ScheduledMaintenanceLogMapper() {
	}

	public static ScheduledMaintenanceLogMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceLogMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenanceLogDTO domainToDto(ScheduledMaintenanceLog domain) throws Exception {
		ScheduledMaintenanceLogDTO dto = new ScheduledMaintenanceLogDTO();

		dto.setId(domain.getId());
		dto.setNotes(domain.getNotes());
		dto.setLogType(domain.getLogType());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setUserName(domain.getCreatedUser().getFullName());

		if (domain.getScheduledMaintenance() != null) {
			dto.setScheduledMaintenanceId(domain.getScheduledMaintenance().getId());
			dto.setScheduledMaintenanceCode(domain.getScheduledMaintenance().getCode());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(ScheduledMaintenanceLogDTO dto, ScheduledMaintenanceLog domain) throws Exception {
		domain.setId(dto.getId());
		domain.setNotes(dto.getNotes());
		domain.setLogType(dto.getLogType());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ScheduledMaintenanceLogDTO domainToDtoForDataTable(ScheduledMaintenanceLog domain) throws Exception {
		ScheduledMaintenanceLogDTO dto = new ScheduledMaintenanceLogDTO();
		dto.setId(domain.getId());
		dto.setNotes(domain.getNotes());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setUserName(domain.getCreatedUser().getFullName());

		return dto;
	}

}
