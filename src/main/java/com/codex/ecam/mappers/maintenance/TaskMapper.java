package com.codex.ecam.mappers.maintenance;

import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.task.Task;

public class TaskMapper extends GenericMapper<Task, TaskDTO> {

	private static TaskMapper instance = null;

	private TaskMapper() {
	}

	public static TaskMapper getInstance() {
		if (instance == null) {
			instance = new TaskMapper();
		}

		return instance;
	}

	@Override
	public TaskDTO domainToDto(Task domain) throws Exception {
		TaskDTO dto = new TaskDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setTaskType(domain.getTaskType());
		dto.setTaskTypeId(domain.getTaskType().getId());
		dto.setTaskTypeName(domain.getTaskType().getName());
		dto.setDescription(domain.getDescription());
		dto.setEstimatedHours(domain.getEstimatedHours());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(TaskDTO dto, Task domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setTaskType(dto.getTaskType());
		domain.setDescription(dto.getDescription());
		domain.setEstimatedHours(dto.getEstimatedHours());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public TaskDTO domainToDtoForDataTable(Task domain) throws Exception {
		TaskDTO dto = new TaskDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setTaskType(domain.getTaskType());
		dto.setDescription(domain.getDescription());
		dto.setEstimatedHours(domain.getEstimatedHours());

		return dto;
	}

}

