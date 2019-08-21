package com.neolith.focus.controller.biz.warehouse;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.dto.biz.warehouse.WareHouseDTO;
import com.neolith.focus.repository.FocusDataTablesInput; 
import com.neolith.focus.service.biz.api.WareHouseService; 

@RestController
@RequestMapping(WarehouseRestController.REQUEST_MAPPING_URL)
public class WarehouseRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/wearhouse";

	@Autowired
	private WareHouseService wearHouseService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<WareHouseDTO> warehouseList(@Valid FocusDataTablesInput input) {
		try {
			return wearHouseService.findWearHouseByType(input, AssetCategoryType.WAREHOUSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/warehousebylevel", method = RequestMethod.GET)
	public DataTablesOutput<WareHouseDTO> findWarehouseByLevel(@Valid FocusDataTablesInput input,Integer businessId) {
		try {
			return wearHouseService.findWarehouseByBusiness(input, AssetCategoryType.WAREHOUSE,businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/warehouseWithItemQty", method = RequestMethod.GET)
	public DataTablesOutput<WareHouseDTO> warehouseWithRemainQty(@Valid FocusDataTablesInput input, @Valid Integer partId) {
		try {
			return wearHouseService.findWarehouseWithRemainQty(input, AssetCategoryType.WAREHOUSE, partId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/warehouselist", method = RequestMethod.GET)
	public List<WareHouseDTO> warehouseList(Integer id) {
		try {
			return wearHouseService.findWarehouseList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/parentwarehouselist", method = RequestMethod.GET)
	public List<WareHouseDTO> parentwarehouselist() {
		try {
			return wearHouseService.findParentWarehouseList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
