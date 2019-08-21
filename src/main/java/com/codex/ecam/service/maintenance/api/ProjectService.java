package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.ProjectDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.maintenance.ProjectResult;

public interface ProjectService {

    ProjectResult newProject();

    ProjectResult findById(Integer id) throws Exception;

    ProjectResult save(ProjectDTO project);

    ProjectResult update(ProjectDTO project);

    ProjectResult delete(Integer id);

    DataTablesOutput<ProjectDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

    DataTablesOutput<ProjectDTO> findProjectByBusiness(FocusDataTablesInput input, Integer id);

}
