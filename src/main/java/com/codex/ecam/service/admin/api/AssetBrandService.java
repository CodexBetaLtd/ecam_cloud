package com.codex.ecam.service.admin.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetBrandResult;

public interface AssetBrandService {

	AssetBrandDTO findById(Integer id)throws Exception;

	AssetBrandResult delete(Integer id);

	AssetBrandResult save(AssetBrandDTO assetBrandDTO)throws Exception;

	DataTablesOutput<AssetBrandDTO> findAll(FocusDataTablesInput input)throws Exception;

	List<AssetBrandDTO> findAll();

}
