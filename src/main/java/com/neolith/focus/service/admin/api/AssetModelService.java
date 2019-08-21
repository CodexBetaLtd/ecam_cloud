package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.AssetModelDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.AssetModelResult;

public interface AssetModelService {

	AssetModelDTO findById(Integer id) throws Exception;

	AssetModelResult delete(Integer id);

	AssetModelResult save(AssetModelDTO assetModelDTO) throws Exception;

	DataTablesOutput<AssetModelDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetModelDTO> findAllByBrand(FocusDataTablesInput input, Integer id) throws Exception;

	List<AssetModelDTO> findAll();

	List<AssetModelDTO> findByBrandId(Integer id);

}
