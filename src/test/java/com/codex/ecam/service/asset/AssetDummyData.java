package com.codex.ecam.service.asset;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetDTO;

public class AssetDummyData {
	
private static AssetDummyData instance = null;
	
	public static AssetDummyData getInstance() {
		if (instance == null) {
			instance = new AssetDummyData();
		}
		return instance;
	}
	
	public AssetDTO getDummyDTOFaciltyAsset() {		
		AssetDTO assetDTO = new AssetDTO();		
		
		assetDTO.setName("Facility_Dummy_1");
		assetDTO.setCode("F_0001");
		assetDTO.setDescription("This Dummy data for Machine description");
		assetDTO.setIsOnline(true);
		assetDTO.setAssetCategoryId(1);
				
		assetDTO.setNotes("This Dummy data for Machine Notes");
		assetDTO.setAssetCategoryType(AssetCategoryType.LOCATIONS_OR_FACILITIES);
		
		assetDTO.setAddress("Dalugama, Kelaniya");
		assetDTO.setCity("Colombo");
		assetDTO.setProvince("Western");
		assetDTO.setPostalCode("85000");
		assetDTO.setCountryId(1);
		
		assetDTO.setBrand(1);
		assetDTO.setModel(1);
		assetDTO.setSerialNo("00012345");		
		
		return assetDTO;
	}
	
	public AssetDTO getDummyDTOToolsAsset() {		
		AssetDTO assetDTO = new AssetDTO();		
		
		assetDTO.setName("Tool_Dummy_2");
		assetDTO.setCode("T_0001");
		assetDTO.setDescription("This Dummy data for Tool description");
		assetDTO.setIsOnline(true);
		assetDTO.setAssetCategoryId(1);
				
		assetDTO.setNotes("This Dummy data for Tool Notes");
		assetDTO.setAssetCategoryType(AssetCategoryType.TOOLS);
		
		assetDTO.setAddress("kurunegala road, Rambukkana");
		assetDTO.setCity("Kegalle");
		assetDTO.setProvince("Sambaragamuwa");
		assetDTO.setPostalCode("85500");
		assetDTO.setCountryId(1);
		
		assetDTO.setBrand(1);
		assetDTO.setModel(1);
		assetDTO.setSerialNo("00012dff5");		
		
		return assetDTO;
	}
	
	public AssetDTO getDummyDTOMachineAsset() {		
		AssetDTO assetDTO = new AssetDTO();		
		
		assetDTO.setName("Machine_Dummy_3");
		assetDTO.setCode("M_0001");
		assetDTO.setDescription("This is Dummy Data for Machine description.");
		assetDTO.setIsOnline(true);
		assetDTO.setAssetCategoryId(1);
				
		assetDTO.setNotes("This Dummy data for Machine Notes");
		assetDTO.setAssetCategoryType(AssetCategoryType.EQUIPMENTS_OR_MACHINES);
		
		assetDTO.setAddress(" no. 11, Rambukkana Road");
		assetDTO.setCity("Kurunegala");
		assetDTO.setProvince("North Western");
		assetDTO.setPostalCode("85660");
		assetDTO.setCountryId(1);
		
		assetDTO.setBrand(1);
		assetDTO.setModel(1);
		assetDTO.setSerialNo("q232200dd");		
		
		return assetDTO;
	}
	
	public List<AssetDTO> getAllDummyData(){
		List<AssetDTO> list = new ArrayList<>(3);
		
		list.add(getDummyDTOFaciltyAsset());
		list.add(getDummyDTOToolsAsset());
		list.add(getDummyDTOMachineAsset());
			
		return list;
		
	}

}
