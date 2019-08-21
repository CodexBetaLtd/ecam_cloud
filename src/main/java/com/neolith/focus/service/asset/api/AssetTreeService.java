package com.neolith.focus.service.asset.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.repository.FocusDataTablesInput;

public interface AssetTreeService {

	List<AssetDTO> findAllChildrens(Integer id) throws Exception;
	
	List<AssetDTO> findAllMachineChildrens(Integer id) throws Exception;

	DataTablesOutput<AssetDTO> findAllParentFacilities(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findAllParentMachinesAndTools(FocusDataTablesInput input) throws Exception;

}
