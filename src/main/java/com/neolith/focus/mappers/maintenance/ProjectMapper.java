package com.neolith.focus.mappers.maintenance;

import com.neolith.focus.dto.maintenance.ProjectDTO;
import com.neolith.focus.exception.setting.ProjectException;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.mappers.admin.UserMapper;
import com.neolith.focus.model.maintenance.project.Project;
import com.neolith.focus.model.maintenance.project.ProjectUser;

public class ProjectMapper extends GenericMapper<Project, ProjectDTO> {

	private static ProjectMapper instance = null;

	private ProjectMapper() {
	}

	public static ProjectMapper getInstance() {
		if (instance == null) {
			instance = new ProjectMapper();
		}
		return instance;
	}

	@Override
	public ProjectDTO domainToDto(Project domain) throws Exception {
		ProjectDTO dto = new ProjectDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setNotes(domain.getNotes());
		dto.setActualEndDate(domain.getActualEndDate());
		dto.setActualStartDate(domain.getActualStartDate());
		dto.setProjectedEndDate(domain.getProjectedEndDate());
		dto.setProjectedStartDate(domain.getProjectedStartDate());

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}

		setUsers(domain, dto);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setUsers(Project domain, ProjectDTO dto) throws Exception {
		if (domain.getProjectUsers().size() > 0) {
			for (ProjectUser projectUser : domain.getProjectUsers()) {
				dto.getUsers().add(UserMapper.getInstance().domainToViewDto(projectUser.getUser()));
			}
		}
	}

	@Override
	public void dtoToDomain(ProjectDTO dto, Project domain) throws ProjectException {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setNotes(dto.getNotes());
		domain.setActualEndDate(dto.getActualEndDate());
		domain.setActualStartDate(dto.getActualStartDate());
		domain.setProjectedEndDate(dto.getProjectedEndDate());
		domain.setProjectedStartDate(dto.getProjectedStartDate());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ProjectDTO domainToDtoForDataTable(Project domain) throws Exception {
		ProjectDTO dto = new ProjectDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
