package com.neolith.focus.service.app.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.app.AppDTO;
import com.neolith.focus.dto.app.MenuDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.app.AppResult;

public interface AppService {

	AppDTO findById(Integer id) throws Exception;

	AppResult delete(Integer id);

	AppResult save(AppDTO app) throws Exception;

	DataTablesOutput<AppDTO> findAll(FocusDataTablesInput input) throws Exception;

	List<MenuDTO> findAllMenus();

	Boolean isAppInstalled(Integer appId);

	List<AppDTO> findRelatedApps(Integer appId);

	Boolean installApp(Integer appId);

	Boolean uninstallApp(Integer appId);

	List<AppDTO> findAffectedApps(Integer appId);

	List<AppDTO> findAllApps() throws Exception;

}
