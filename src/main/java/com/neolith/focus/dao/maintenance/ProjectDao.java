package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.project.Project;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends FocusDataTableRepository<Project, Integer> {

    Project findById(Integer id);

}
