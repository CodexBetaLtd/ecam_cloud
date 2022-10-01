package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.Priority;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface PriorityDao extends FocusDataTableRepository<Priority, Integer> {

    Priority findById(Integer id);

}
