package com.codex.ecam.mappers.maintenance;

import com.codex.ecam.dto.maintenance.task.TaskGroupDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.task.TaskGroup;

public class TaskGroupMapper extends GenericMapper<TaskGroup, TaskGroupDTO> {

	private static TaskGroupMapper instance = null;

	private TaskGroupMapper() {
	}

	public static TaskGroupMapper getInstance() {
		if (instance == null) {
			instance = new TaskGroupMapper();
		}
		return instance;
	}

	@Override
	public TaskGroupDTO domainToDto(TaskGroup domain) throws Exception {
		TaskGroupDTO dto = new TaskGroupDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}
		if ((domain.getTasks() != null) && (domain.getTasks().size() > 0)) {
			dto.setTasks(TaskMapper.getInstance().domainToDTOList(domain.getTasks()));
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}


	@Override
	public void dtoToDomain(TaskGroupDTO dto, TaskGroup domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setActive(dto.getActive());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public TaskGroupDTO domainToDtoForDataTable(TaskGroup domain) throws Exception {
		TaskGroupDTO dto = new TaskGroupDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());		
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}


}
