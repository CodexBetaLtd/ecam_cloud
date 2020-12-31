package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.UserJobTitle;
import com.codex.ecam.repository.FocusDataTableRepository;
@Repository
public interface UserJobTitleDao extends FocusDataTableRepository<UserJobTitle, Integer> {

	UserJobTitle findById(Integer id);

}
