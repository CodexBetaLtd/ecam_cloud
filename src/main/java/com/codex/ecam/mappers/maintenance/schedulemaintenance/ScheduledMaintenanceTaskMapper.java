package com.codex.ecam.mappers.maintenance.schedulemaintenance;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;

public class ScheduledMaintenanceTaskMapper extends GenericMapper<ScheduledMaintenanceTask, ScheduledMaintenanceTaskDTO> {

	private static ScheduledMaintenanceTaskMapper instance = null;

	private ScheduledMaintenanceTaskMapper() {
	}

	public static ScheduledMaintenanceTaskMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceTaskMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenanceTaskDTO domainToDto(ScheduledMaintenanceTask domain) throws Exception {
		ScheduledMaintenanceTaskDTO dto = new ScheduledMaintenanceTaskDTO();
		dto.setId(domain.getId());
		dto.setDescription(domain.getDescription());
		dto.setEstimatedHours(domain.getEstimatedHours());
		dto.setTaskType(domain.getTaskType());

		if (domain.getAssignedUser() != null) {
			dto.setUserId(domain.getAssignedUser().getId());
			dto.setUserName(domain.getAssignedUser().getFullName());
		}

		if ( domain.getScheduledMaintenanceTrigger() != null ) {
			ScheduledMaintenanceTrigger trigger = domain.getScheduledMaintenanceTrigger();
			dto.setTriggerId(trigger.getId());
			dto.setAssetId(trigger.getAsset().getId());
			dto.setAssetName(trigger.getAsset().getName());
			dto.setScheduledMaintenanceId(domain.getScheduledMaintenanceTrigger().getScheduledMaintenance().getId());
		}

		if ( domain.getTaskGroup() != null ) {
			dto.setTaskGroupId(domain.getTaskGroup().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(ScheduledMaintenanceTaskDTO dto, ScheduledMaintenanceTask domain) throws Exception {
		domain.setId(dto.getId());
		domain.setDescription(dto.getDescription());
		domain.setEstimatedHours(dto.getEstimatedHours());
		domain.setTaskType(dto.getTaskType());
		domain.setTaskIndex(dto.getIndex());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ScheduledMaintenanceTaskDTO domainToDtoForDataTable(ScheduledMaintenanceTask domain) throws Exception {
		ScheduledMaintenanceTaskDTO dto = new ScheduledMaintenanceTaskDTO();
		dto.setId(domain.getId());
		dto.setDescription(domain.getDescription());
		dto.setEstimatedHours(domain.getEstimatedHours());
		dto.setTaskType(domain.getTaskType());
		if (domain.getAssignedUser() != null) {
			dto.setUserName(domain.getAssignedUser().getFullName());
		}
		return dto;
	}

}
