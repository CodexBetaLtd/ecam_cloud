package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.UserSkillLevel;
import com.codex.ecam.repository.FocusDataTableRepository;
@Repository
public interface UserSkillLevelDao extends FocusDataTableRepository<UserSkillLevel, Integer> {

	UserSkillLevel findById(Integer id);

}
