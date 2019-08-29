package com.codex.ecam.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserGroupResult;
import com.codex.ecam.util.type.GenericCheckBox;

public interface UserGroupService {

	DataTablesOutput<UserGroupDTO> findAll(FocusDataTablesInput input) throws Exception;

	UserGroupDTO findById(Integer id) throws Exception;

	UserGroupResult delete(Integer id);

	UserGroupResult save(UserGroupDTO userGroup) throws Exception;

	List<UserGroupDTO> findAll() throws Exception;

	List<GenericCheckBox<Menu, SubMenu>> getMenuPermissions();

	List<Page> findPageListByBusiness();

}