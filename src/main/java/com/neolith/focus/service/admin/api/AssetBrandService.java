package com.neolith.focus.service.admin.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.AssetBrandDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.AssetBrandResult;

public interface AssetBrandService {

	AssetBrandDTO findById(Integer id)throws Exception;

	AssetBrandResult delete(Integer id);

	AssetBrandResult save(AssetBrandDTO assetBrandDTO)throws Exception;

	DataTablesOutput<AssetBrandDTO> findAll(FocusDataTablesInput input)throws Exception;

	List<AssetBrandDTO> findAll();

}
