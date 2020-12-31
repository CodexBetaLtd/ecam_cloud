package com.codex.ecam.mappers.maintenance.workorder;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderTaskDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrderTask;
import com.codex.ecam.util.DateUtil;

public class WorkOrderTaskMapper extends GenericMapper<WorkOrderTask, WorkOrderTaskDTO> {

	private static WorkOrderTaskMapper instance = null;

	private WorkOrderTaskMapper() {}

	public static WorkOrderTaskMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderTaskMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderTaskDTO domainToDto(WorkOrderTask domain) throws Exception {
		WorkOrderTaskDTO dto = new WorkOrderTaskDTO();
		dto.setId(domain.getId());
		dto.setWorkOrderId(domain.getWorkOrder() != null ? domain.getWorkOrder().getId() : null);
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setCompletionNote(domain.getCompletionNote());
		dto.setAssignedAssetId(domain.getAsset() != null ? domain.getAsset().getId() : null);
		dto.setAssignedAssetName(domain.getAsset() != null ? domain.getAsset().getName() : "");
		dto.setAssignedUserId(domain.getAssignedUser() != null ? domain.getAssignedUser().getId() : null);
		dto.setAssignedUserName(domain.getAssignedUser() != null ? domain.getAssignedUser().getFullName() : "");
		dto.setStartedDate(DateUtil.getSimpleDateString(domain.getStartedDate()));
		dto.setEstimatedHours(domain.getEstimatedHours());
		dto.setCompletedUserId(domain.getCompletedUser() != null ? domain.getCompletedUser().getId() : null);
		dto.setCompletedUserName(domain.getCompletedUser() != null ? domain.getCompletedUser().getFullName() : "");
		dto.setCompletedDate(DateUtil.getSimpleDateString(domain.getCompletedDate()));
		dto.setSpentHours(domain.getSpentHours());
		dto.setCompletionRemark(domain.getCompletionRemark());

		setTaskType(domain.getTaskType(), dto);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(WorkOrderTaskDTO dto, WorkOrderTask domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setCompletionNote(dto.getCompletionNote());
		domain.setStartedDate(DateUtil.getDateObj(dto.getStartedDate()));
		domain.setEstimatedHours(dto.getEstimatedHours());
		domain.setCompletedDate(DateUtil.getDateObj(dto.getCompletedDate()));
		domain.setCompletionNote(dto.getCompletionNote());
		domain.setCompletionRemark(dto.getCompletionRemark());
		domain.setSpentHours(dto.getSpentHours());

		setCommanDomainFields(dto, domain);
	}

	private void setTaskType(TaskType taskType, WorkOrderTaskDTO dto) {
		if ( taskType != null ) {
			dto.setTaskTypeId(taskType.getId());
			dto.setTaskTypeDescription(taskType.getName());
			dto.setTaskType(taskType);
		}
	}

	@Override
	public WorkOrderTaskDTO domainToDtoForDataTable(WorkOrderTask domain) throws Exception {
		WorkOrderTaskDTO dto = new WorkOrderTaskDTO();
		dto.setId(domain.getId());
		dto.setWorkOrderId(domain.getWorkOrder() != null ? domain.getWorkOrder().getId() : null);
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setCompletionNote(domain.getCompletionNote());
		if (domain.getAssignedUser() != null) {			
			dto.setAssignedUserName(domain.getAssignedUser().getFullName());
		}

		return dto;
	}

}
