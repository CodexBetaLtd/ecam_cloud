package com.neolith.focus.dao.admin;

import com.neolith.focus.model.maintenance.Priority;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityDao extends FocusDataTableRepository<Priority, Integer> {

    Priority findById(Integer id);

}
