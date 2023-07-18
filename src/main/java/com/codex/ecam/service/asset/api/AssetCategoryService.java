package com.codex.ecam.service.asset.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetCategoryResult;

public interface AssetCategoryService {

	DataTablesOutput<AssetCategoryDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetCategoryDTO> findByAssetCategoyType(FocusDataTablesInput input, AssetCategoryType type) throws Exception;

	DataTablesOutput<AssetCategoryDTO> findByAssetCategoyTypeById(FocusDataTablesInput input, Integer id) throws Exception;

	DataTablesOutput<AssetCategoryDTO> findAllByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

	AssetCategoryDTO findById(Integer id) throws Exception;

	AssetCategoryResult delete(Integer id);

	AssetCategoryResult update(AssetCategoryDTO assetCategoryDTO);

	AssetCategoryResult save(AssetCategoryDTO assetCategoryDTO);

	List<AssetCategoryDTO> findByAssetCategoyType(AssetCategoryType type);

	List<AssetCategoryDTO> findAllBySystemCode(Integer systemCode);

	List<AssetCategoryDTO> findAll();

	Integer findParentIdSystemCode(Integer parentId) throws Exception;

	void deleteAll();

	void saveAll(List<AssetCategoryDTO> allData);

	AssetCategoryResult deleteMultiple(Integer[] ids) throws Exception;




}

