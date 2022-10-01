package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.MaintenanceType;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface MaintenanceTypeDao extends FocusDataTableRepository<MaintenanceType, Integer> {

    MaintenanceType findById(Integer id);

}
