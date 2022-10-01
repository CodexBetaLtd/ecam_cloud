package com.codex.ecam.service.admin.api;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.Menu;
import com.codex.ecam.constants.Page;
import com.codex.ecam.constants.PagePermission;
import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.dto.admin.PermisonTreeDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
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

	List<PermisonTreeDTO> getMenuPermissionsAll(Integer id);

	List<PermisonTreeDTO> getMenuAll();

	List<PermisonTreeDTO> getSubMenuAll(Integer id);

	List<PermisonTreeDTO> getPageAll(Integer id);

	List<PermisonTreeDTO> getPagePermistionAll(Integer id);

	List<Page> findPageListByBusiness();

	List<GenericCheckBox<Page, PagePermission>> findPagePermissions();

	UserGroupResult deleteMultiple(Integer[] ids) throws Exception;

}
