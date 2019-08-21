package com.neolith.focus.service.biz.api;
import java.util.List;


import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.dto.biz.warehouse.WareHouseDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.asset.WareHouseResult;

public interface WareHouseService {

	DataTablesOutput<WareHouseDTO> findWearHouseByType(FocusDataTablesInput input,AssetCategoryType type) throws Exception;

	WareHouseDTO findById(Integer id) throws Exception;

	WareHouseResult delete(Integer id) throws Exception;

	WareHouseResult save(WareHouseDTO dto) throws Exception;

	DataTablesOutput<WareHouseDTO> findWarehouseWithRemainQty(FocusDataTablesInput input, AssetCategoryType categoryType, Integer partId);
	
	List<WareHouseDTO> findWarehouseList(Integer id);
	
	List<WareHouseDTO> findParentWarehouseList();

	DataTablesOutput<WareHouseDTO> findWarehouseByBusiness(FocusDataTablesInput input, AssetCategoryType warehouse,Integer id);

}
