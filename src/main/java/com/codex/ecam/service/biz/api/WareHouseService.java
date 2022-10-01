package com.codex.ecam.service.biz.api;
import java.util.List;


import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.result.asset.WareHouseResult;

public interface WareHouseService {

	DataTablesOutput<WareHouseDTO> findWearHouseByType(FocusDataTablesInput input,AssetCategoryType type) throws Exception;

	WareHouseDTO findById(Integer id) throws Exception;

	WareHouseResult delete(Integer id) throws Exception;

	WareHouseResult save(WareHouseDTO dto) throws Exception;

	DataTablesOutput<WareHouseDTO> findWarehouseWithRemainQty(FocusDataTablesInput input, AssetCategoryType categoryType, Integer partId);

	List<WareHouseDTO> findWarehouseList(Integer id);

	List<WareHouseDTO> findParentWarehouseList();

	DataTablesOutput<WareHouseDTO> findWarehouseByBusiness(FocusDataTablesInput input, AssetCategoryType warehouse,Integer id);

	WareHouseResult deleteMultiple(Integer[] ids) throws Exception;

}
