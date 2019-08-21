package com.neolith.focus.result.maintenance;

import com.neolith.focus.dto.maintenance.ProjectDTO;
import com.neolith.focus.model.maintenance.project.Project;
import com.neolith.focus.result.BaseResult;

public class ProjectResult extends BaseResult<Project, ProjectDTO> {

	public ProjectResult(Project domain, ProjectDTO dto) {
		super(domain, dto);
	}
	
	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
