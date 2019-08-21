package com.codex.ecam.mappers.maintenance.workorder;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderFileDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrderFile;

public class WorkOrderFileMapper extends GenericMapper<WorkOrderFile, WorkOrderFileDTO>{

	private static WorkOrderFileMapper instance = null;

	private WorkOrderFileMapper() {
	}

	public static WorkOrderFileMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderFileMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderFileDTO domainToDto(WorkOrderFile domain) throws Exception {
		WorkOrderFileDTO dto = new WorkOrderFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public WorkOrderFileDTO domainToDtoForDataTable(WorkOrderFile domain) throws Exception {
		WorkOrderFileDTO dto = new WorkOrderFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		return dto;
	}

	@Override
	public void dtoToDomain(WorkOrderFileDTO dto, WorkOrderFile domain) throws Exception {
		domain.setId(dto.getId());
		domain.setItemDescription(dto.getItemDescription());
		domain.setFileLocation(dto.getFileLocation());
		domain.setFileType(dto.getFileType());
		domain.setFileDate(dto.getFileDate());

		setCommanDomainFields(dto, domain);
	}

}
