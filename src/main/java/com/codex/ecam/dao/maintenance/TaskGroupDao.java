package com.codex.ecam.dao.maintenance;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.task.TaskGroup;
import com.codex.ecam.repository.FocusDataTableRepository;


@Repository
public interface TaskGroupDao extends FocusDataTableRepository<TaskGroup, Integer> {


}
