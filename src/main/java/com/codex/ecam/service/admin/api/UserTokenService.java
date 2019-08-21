package com.codex.ecam.service.admin.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.dto.admin.UserTokenDTO;

public interface UserTokenService {
	
	DataTablesOutput<UserGroupDTO> findAll(DataTablesInput input) throws Exception;

	UserTokenDTO findById(Integer id) throws Exception;

	UserTokenDTO findbytoken(String token) throws Exception;

	void delete(Integer id);

	Integer upate(UserTokenDTO userTokenDTO) throws Exception;

	Integer save(UserTokenDTO tokenDTO) throws Exception;

}
