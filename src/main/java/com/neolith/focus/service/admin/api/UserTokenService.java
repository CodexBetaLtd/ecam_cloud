package com.neolith.focus.service.admin.api;

import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.dto.admin.UserTokenDTO;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface UserTokenService {
	
	DataTablesOutput<UserGroupDTO> findAll(DataTablesInput input) throws Exception;

	UserTokenDTO findById(Integer id) throws Exception;

	UserTokenDTO findbytoken(String token) throws Exception;

	void delete(Integer id);

	Integer upate(UserTokenDTO userTokenDTO) throws Exception;

	Integer save(UserTokenDTO tokenDTO) throws Exception;

}
