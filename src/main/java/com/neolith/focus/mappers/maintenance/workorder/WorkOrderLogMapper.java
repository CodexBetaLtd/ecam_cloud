package com.neolith.focus.mappers.maintenance.workorder;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderLogDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.maintenance.workorder.WorkOrderLog;

public class WorkOrderLogMapper extends GenericMapper<WorkOrderLog, WorkOrderLogDTO> {

	private static WorkOrderLogMapper instance = null;

	private WorkOrderLogMapper() {
	}

	public static WorkOrderLogMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderLogMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderLogDTO domainToDto(WorkOrderLog domain) throws Exception {
		WorkOrderLogDTO dto = new WorkOrderLogDTO();

		dto.setId(domain.getId());
		dto.setNotes(domain.getNotes());
		dto.setLogType(domain.getLogType());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setUserName(domain.getCreatedUser().getFullName());

		if (domain.getWorkOrder() != null) {
			dto.setWorkOrderId(domain.getWorkOrder().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(WorkOrderLogDTO dto, WorkOrderLog domain) throws Exception {
		domain.setId(dto.getId());
		domain.setNotes(dto.getNotes());
		domain.setLogType(dto.getLogType());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public WorkOrderLogDTO domainToDtoForDataTable(WorkOrderLog domain) throws Exception {
		WorkOrderLogDTO dto = new WorkOrderLogDTO();
		dto.setId(domain.getId());
		dto.setNotes(domain.getNotes());
		dto.setCreatedDate(domain.getCreatedDate());
		dto.setUserName(domain.getCreatedUser().getFullName());

		return dto;
	}

}
