package com.codex.ecam.service.asset.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface AssetTreeService {

	List<AssetDTO> findAllChildrens(Integer id) throws Exception;
	
	List<AssetDTO> findAllMachineChildrens(Integer id) throws Exception;

	DataTablesOutput<AssetDTO> findAllParentFacilities(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findAllParentMachinesAndTools(FocusDataTablesInput input) throws Exception;
	DataTablesOutput<AssetDTO> findMachineParentLocation(FocusDataTablesInput input,Integer id,Integer assetId) throws Exception;

}
