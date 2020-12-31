package com.codex.ecam.service.biz.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface WareHouseTreeService {

	List<WareHouseDTO> findAllChildrens(Integer id) throws Exception;
	
	List<AssetDTO> findAllWareHouseChildrens(Integer id) throws Exception;

	DataTablesOutput<WareHouseDTO> findAllParentFacilities(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findAllParentMachinesAndTools(FocusDataTablesInput input) throws Exception;

}
