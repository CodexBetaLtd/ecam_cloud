package com.codex.ecam.service.log.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.AssetLogDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface AssetLogService {

	DataTablesOutput<AssetLogDTO> findAllByAssetId(FocusDataTablesInput input, Integer id) throws Exception;
	
}
