package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserJobTitle;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;
@Repository
public interface UserJobTitleDao extends FocusDataTableRepository<UserJobTitle, Integer> {

	UserJobTitle findById(Integer id);

}
