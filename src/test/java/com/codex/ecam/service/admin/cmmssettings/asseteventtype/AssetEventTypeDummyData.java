package com.codex.ecam.service.admin.cmmssettings.asseteventtype;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.admin.AssetEventTypeDTO;

public class AssetEventTypeDummyData {

	private static AssetEventTypeDummyData instance = null;
	
	public static AssetEventTypeDummyData getInstance() {
		if (instance == null) {
			instance = new AssetEventTypeDummyData();
		}
		return instance;
	}
	
	public AssetEventTypeDTO getDummyDTOAssetEventTypeOne() {
		
		AssetEventTypeDTO assetEventTypeDTO = new AssetEventTypeDTO();
		assetEventTypeDTO.setName("Test Asset Event type 1");
		assetEventTypeDTO.setDescription("Description 1");		
		assetEventTypeDTO.setCode("Code 1");		
				
		return assetEventTypeDTO;
}
	
	public AssetEventTypeDTO getDummyDTOAssetEventTypeTwo() {
		
		AssetEventTypeDTO assetEventTypeDTO = new AssetEventTypeDTO();
		assetEventTypeDTO.setName("Test Asset Event type 2");
		assetEventTypeDTO.setDescription("Description 2");		
		assetEventTypeDTO.setCode("Code 2");	
		
				
		return assetEventTypeDTO;
}
	
	public List<AssetEventTypeDTO> getAllDummyData(){
		List<AssetEventTypeDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOAssetEventTypeOne());
		list.add(getDummyDTOAssetEventTypeTwo());
			
		return list;
		
	}
}
