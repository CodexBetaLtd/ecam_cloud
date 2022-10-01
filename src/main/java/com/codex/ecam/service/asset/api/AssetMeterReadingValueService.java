package com.codex.ecam.service.asset.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface AssetMeterReadingValueService {

	DataTablesOutput<AssetMeterReadingValueDTO> findMeterReadingHistory(FocusDataTablesInput input, Integer id) throws Exception;

	AssetMeterReadingValueDTO findMeterReadingValueById(Integer id) throws Exception;

}
