package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.constants.Menu;
import com.neolith.focus.constants.Page;
import com.neolith.focus.constants.SubMenu;
import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.UserGroupResult;
import com.neolith.focus.util.type.GenericCheckBox;

public interface UserGroupService {

	DataTablesOutput<UserGroupDTO> findAll(FocusDataTablesInput input) throws Exception;

	UserGroupDTO findById(Integer id) throws Exception;

	UserGroupResult delete(Integer id);

	UserGroupResult save(UserGroupDTO userGroup) throws Exception;

	List<UserGroupDTO> findAll() throws Exception;

	List<GenericCheckBox<Menu, SubMenu>> getMenuPermissions();

	List<Page> findPageListByBusiness();

}
