package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserSkillLevel;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;
@Repository
public interface UserSkillLevelDao extends FocusDataTableRepository<UserSkillLevel, Integer> {

	UserSkillLevel findById(Integer id);

}
