package com.codex.ecam.mappers.maintenance.workorder;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderNotificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotification;

public class WorkOrderNotificationMapper extends GenericMapper<WorkOrderNotification, WorkOrderNotificationDTO> {

	private static WorkOrderNotificationMapper instance = null;

	private WorkOrderNotificationMapper() {
	}

	public static WorkOrderNotificationMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderNotificationMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderNotificationDTO domainToDto(WorkOrderNotification domain) throws Exception {
		WorkOrderNotificationDTO dto = new WorkOrderNotificationDTO();
		dto.setId(domain.getId());
		dto.setNotifyOnAssignment(domain.getNotifyOnAssignment());
		dto.setNotifyOnStatusChange(domain.getNotifyOnStatusChange());
		dto.setNotifyOnCompletion(domain.getNotifyOnCompletion());
		dto.setNotifyOnTaskCompleted(domain.getNotifyOnTaskCompleted());
		dto.setNotifyOnOnlineOffline(domain.getNotifyOnOnlineOffline());

		if ((domain.getUser() != null) && (domain.getUser().getId() != null)) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(WorkOrderNotificationDTO dto, WorkOrderNotification domain) throws Exception {
		domain.setId(dto.getId());
		domain.setNotifyOnAssignment(dto.getNotifyOnAssignment());
		domain.setNotifyOnStatusChange(dto.getNotifyOnStatusChange());
		domain.setNotifyOnCompletion(dto.getNotifyOnCompletion());
		domain.setNotifyOnTaskCompleted(dto.getNotifyOnTaskCompleted());
		domain.setNotifyOnOnlineOffline(dto.getNotifyOnOnlineOffline());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public WorkOrderNotificationDTO domainToDtoForDataTable(WorkOrderNotification domain) throws Exception {
		WorkOrderNotificationDTO dto = new WorkOrderNotificationDTO();
		dto.setId(domain.getId());
		dto.setNotifyOnAssignment(domain.getNotifyOnAssignment());
		dto.setNotifyOnStatusChange(domain.getNotifyOnStatusChange());
		dto.setNotifyOnCompletion(domain.getNotifyOnCompletion());
		dto.setNotifyOnTaskCompleted(domain.getNotifyOnTaskCompleted());
		dto.setNotifyOnOnlineOffline(domain.getNotifyOnOnlineOffline());

		if ((domain.getUser() != null) && (domain.getUser().getId() != null)) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}

		return dto;
	}


}
