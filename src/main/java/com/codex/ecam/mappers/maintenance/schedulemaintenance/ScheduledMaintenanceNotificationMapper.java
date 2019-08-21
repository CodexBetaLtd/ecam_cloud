package com.codex.ecam.mappers.maintenance.schedulemaintenance;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceNotificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceNotification;

public class ScheduledMaintenanceNotificationMapper extends GenericMapper<ScheduledMaintenanceNotification, ScheduledMaintenanceNotificationDTO> {

	private static ScheduledMaintenanceNotificationMapper instance = null;

	private ScheduledMaintenanceNotificationMapper() {
	}

	public static ScheduledMaintenanceNotificationMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceNotificationMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenanceNotificationDTO domainToDto(ScheduledMaintenanceNotification domain) throws Exception {
		ScheduledMaintenanceNotificationDTO dto = new ScheduledMaintenanceNotificationDTO();
		dto.setId(domain.getId());
		dto.setScheduledMaintenanceId(domain.getScheduledMaintenance().getId());
		dto.setNotifyOnAssignment(domain.getNotifyOnAssignment() == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);
		dto.setNotifyOnStatusChange(domain.getNotifyOnStatusChange() == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);
		dto.setNotifyOnCompletion(domain.getNotifyOnCompletion() == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);
		dto.setNotifyOnTaskCompleted(domain.getNotifyOnTaskCompleted() == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);
		dto.setNotifyOnOnlineOffline(domain.getNotifyOnOnlineOffline() == Boolean.TRUE ? Boolean.TRUE : Boolean.FALSE);

		if ((domain.getUser() != null) && (domain.getUser().getId() != null)) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(ScheduledMaintenanceNotificationDTO dto, ScheduledMaintenanceNotification domain) throws Exception {
		domain.setId(dto.getId());
		domain.setNotifyOnAssignment(dto.getNotifyOnAssignment());
		domain.setNotifyOnStatusChange(dto.getNotifyOnStatusChange());
		domain.setNotifyOnCompletion(dto.getNotifyOnCompletion());
		domain.setNotifyOnTaskCompleted(dto.getNotifyOnTaskCompleted());
		domain.setNotifyOnOnlineOffline(dto.getNotifyOnOnlineOffline());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ScheduledMaintenanceNotificationDTO domainToDtoForDataTable(ScheduledMaintenanceNotification domain)throws Exception {
		ScheduledMaintenanceNotificationDTO dto = new ScheduledMaintenanceNotificationDTO();
		dto.setId(domain.getId());
		dto.setNotifyOnAssignment(domain.getNotifyOnAssignment());
		dto.setNotifyOnCompletion(domain.getNotifyOnCompletion());
		dto.setNotifyOnOnlineOffline(domain.getNotifyOnOnlineOffline());
		dto.setNotifyOnTaskCompleted(domain.getNotifyOnTaskCompleted());
		if ((domain.getUser() != null) && (domain.getUser().getId() != null)) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}

		return dto;
	}

}
