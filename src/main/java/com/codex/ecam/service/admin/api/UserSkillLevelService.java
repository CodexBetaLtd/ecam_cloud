package com.codex.ecam.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.UserSkillLevelDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserSkillLevelResult;

public interface UserSkillLevelService {

	DataTablesOutput<UserSkillLevelDTO> findAll(FocusDataTablesInput input) throws Exception;

	UserSkillLevelDTO findById(Integer id) throws Exception;

	UserSkillLevelResult delete (Integer id);

	UserSkillLevelResult save(UserSkillLevelDTO dto) throws Exception;

	List<UserSkillLevelDTO> findAll();



}
