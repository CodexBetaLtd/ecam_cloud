package com.codex.ecam.dao.maintenance;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.project.Project;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface ProjectDao extends FocusDataTableRepository<Project, Integer> {

    Project findById(Integer id);

}
