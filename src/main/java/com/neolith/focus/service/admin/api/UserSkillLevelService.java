package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.UserSkillLevelDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.UserSkillLevelResult;

public interface UserSkillLevelService {

	DataTablesOutput<UserSkillLevelDTO> findAll(FocusDataTablesInput input) throws Exception;

	UserSkillLevelDTO findById(Integer id) throws Exception;

	UserSkillLevelResult delete (Integer id);

	UserSkillLevelResult save(UserSkillLevelDTO dto) throws Exception;

	List<UserSkillLevelDTO> findAll();



}
