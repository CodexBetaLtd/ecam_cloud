package com.codex.ecam.service.admin.api;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetEventTypeResult;

public interface AssetEventTypeService {

	AssetEventTypeDTO findById(Integer id) throws Exception;

	AssetEventTypeResult delete(Integer id);

	AssetEventTypeResult save(AssetEventTypeDTO assetEventTypeDTO) throws Exception;

	List<AssetEventTypeDTO> findAll();

	void saveAll(List<AssetEventTypeDTO> list);

	void deleteAll();

	DataTablesOutput<AssetEventTypeDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetEventTypeDTO> getAssetEventTypeByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

	AssetEventTypeResult deleteMultiple(Integer[] ids) throws Exception;

}
