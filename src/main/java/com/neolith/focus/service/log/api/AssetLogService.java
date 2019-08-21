package com.neolith.focus.service.log.api;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.asset.AssetLogDTO;
import com.neolith.focus.repository.FocusDataTablesInput;

public interface AssetLogService {

	DataTablesOutput<AssetLogDTO> findAllByAssetId(FocusDataTablesInput input, Integer id) throws Exception;
	
}
