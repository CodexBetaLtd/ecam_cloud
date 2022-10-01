package com.codex.ecam.service.app.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.app.AppDTO;
import com.codex.ecam.dto.app.MenuDTO;
import com.codex.ecam.dto.app.WigetDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.app.AppResult;

public interface AppService {

	AppDTO findById(Integer id) throws Exception;

	AppResult delete(Integer id);

	AppResult save(AppDTO app) throws Exception;

	DataTablesOutput<AppDTO> findAll(FocusDataTablesInput input) throws Exception;

	List<MenuDTO> findAllMenus();
	List<WigetDTO> findAllWigets();

	Boolean isAppInstalled(Integer appId);

	List<AppDTO> findRelatedApps(Integer appId);

	Boolean installApp(Integer appId);

	Boolean uninstallApp(Integer appId);

	List<AppDTO> findAffectedApps(Integer appId);

	List<AppDTO> findAllApps() throws Exception;
	List<WigetDTO> findAllWigetByUserLevel() throws Exception;

}
