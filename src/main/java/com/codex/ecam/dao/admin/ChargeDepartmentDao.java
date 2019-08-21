package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.ChargeDepartment;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface ChargeDepartmentDao extends FocusDataTableRepository<ChargeDepartment, Integer> {

	ChargeDepartment findById(Integer id);

}
