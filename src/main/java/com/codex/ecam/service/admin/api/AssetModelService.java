package com.codex.ecam.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetModelResult;

public interface AssetModelService {

	AssetModelDTO findById(Integer id) throws Exception;

	AssetModelResult delete(Integer id);

	AssetModelResult save(AssetModelDTO assetModelDTO) throws Exception;

	DataTablesOutput<AssetModelDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetModelDTO> findAllByBrand(FocusDataTablesInput input, Integer id) throws Exception;

	List<AssetModelDTO> findAll();

	List<AssetModelDTO> findByBrandId(Integer id);

}
