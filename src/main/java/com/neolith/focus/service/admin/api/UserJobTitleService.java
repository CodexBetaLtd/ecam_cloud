package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.UserJobTitleDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.UserJobTitleResult;

public interface UserJobTitleService {

	DataTablesOutput<UserJobTitleDTO> findAll(FocusDataTablesInput input) throws Exception;

	UserJobTitleDTO findById(Integer id) throws Exception;

	UserJobTitleResult delete (Integer id);

	UserJobTitleResult save(UserJobTitleDTO jobTitelDTO) throws Exception;

	List<UserJobTitleDTO> findAll();



}
