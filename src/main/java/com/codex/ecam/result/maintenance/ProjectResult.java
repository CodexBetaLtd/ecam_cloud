package com.codex.ecam.result.maintenance;

import com.codex.ecam.dto.maintenance.ProjectDTO;
import com.codex.ecam.model.maintenance.project.Project;
import com.codex.ecam.result.BaseResult;

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
