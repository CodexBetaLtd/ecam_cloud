package com.neolith.focus.dao.admin;

import com.neolith.focus.model.maintenance.MaintenanceType;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceTypeDao extends FocusDataTableRepository<MaintenanceType, Integer> {

    MaintenanceType findById(Integer id);

}
