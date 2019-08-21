package com.neolith.focus.dao.admin;

import com.neolith.focus.model.maintenance.ChargeDepartment;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeDepartmentDao extends FocusDataTableRepository<ChargeDepartment, Integer> {

	ChargeDepartment findById(Integer id);

}
