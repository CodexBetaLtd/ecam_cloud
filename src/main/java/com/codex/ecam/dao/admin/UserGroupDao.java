package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.UserGroup;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface UserGroupDao extends FocusDataTableRepository<UserGroup, Integer> {

	UserGroup findById(Integer id);

}
