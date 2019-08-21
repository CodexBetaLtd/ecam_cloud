package com.neolith.focus.service.maintenance.api;

import com.neolith.focus.dto.maintenance.ProjectDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.maintenance.ProjectResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface ProjectService {

    ProjectResult newProject();

    ProjectResult findById(Integer id) throws Exception;

    ProjectResult save(ProjectDTO project);

    ProjectResult update(ProjectDTO project);

    ProjectResult delete(Integer id);

    DataTablesOutput<ProjectDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

    DataTablesOutput<ProjectDTO> findProjectByBusiness(FocusDataTablesInput input, Integer id);

}
