package com.codex.ecam.service.admin.api;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.UserJobTitleDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserJobTitleResult;

public interface UserJobTitleService {

	DataTablesOutput<UserJobTitleDTO> findAll(FocusDataTablesInput input) throws Exception;

	UserJobTitleDTO findById(Integer id) throws Exception;

	UserJobTitleResult delete (Integer id);

	UserJobTitleResult save(UserJobTitleDTO jobTitelDTO) throws Exception;

	List<UserJobTitleDTO> findAll();

	UserJobTitleResult deleteMultiple(Integer[] ids) throws Exception;



}
