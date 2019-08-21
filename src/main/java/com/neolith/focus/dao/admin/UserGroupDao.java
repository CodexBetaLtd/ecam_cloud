package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserGroup;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupDao extends FocusDataTableRepository<UserGroup, Integer> {

	UserGroup findById(Integer id);

}
