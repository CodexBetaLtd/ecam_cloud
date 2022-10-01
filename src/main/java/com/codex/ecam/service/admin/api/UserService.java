package com.codex.ecam.service.admin.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserResult;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * UserService interface provide the basic user operation
 *
 * @author
 * @version 1.0
 * @since 2016-11-11
 */

public interface UserService {

	List<UserDTO> findAll();

	List<UserDTO> findUserList();

	UserDTO findUserById(Integer userId) throws Exception;

	UserDTO findUser(UserDTO dto);

	UserDTO findById(Integer id) throws Exception;

	User findByEmail(String email) throws Exception;

	User findEntityById(Integer id);

	UserResult save(UserDTO dto) throws Exception;

	UserResult delete(Integer id);

	DataTablesOutput<UserDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

	DataTablesOutput<UserDTO> findAllUsersByGroup(FocusDataTablesInput dataTablesInput, Integer groupId);

	DataTablesOutput<UserDTO> findAllByBusiness(FocusDataTablesInput dataTablesInput, Integer id);

	DataTablesOutput<UserDTO> findAllUsersByLoggedUserBusiness(FocusDataTablesInput input);

	DataTablesOutput<UserDTO> findUsersBySite(FocusDataTablesInput input, Integer id);

	String getSystemPassword();

	byte[] getUserAvatar(Integer id, HttpServletRequest request) throws IOException;

	UserResult deleteMultiple(Integer[] ids) throws Exception;

}
