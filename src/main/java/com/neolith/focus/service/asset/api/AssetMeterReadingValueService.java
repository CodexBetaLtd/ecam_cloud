package com.neolith.focus.service.asset.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.asset.AssetMeterReadingValueDTO;
import com.neolith.focus.repository.FocusDataTablesInput;

public interface AssetMeterReadingValueService {

	DataTablesOutput<AssetMeterReadingValueDTO> findMeterReadingHistory(FocusDataTablesInput input, Integer id) throws Exception;

	AssetMeterReadingValueDTO findMeterReadingValueById(Integer id) throws Exception;

}
