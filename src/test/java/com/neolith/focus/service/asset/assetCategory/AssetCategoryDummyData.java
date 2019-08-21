package com.neolith.focus.service.asset.assetCategory;

import com.neolith.focus.dto.asset.AssetCategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class AssetCategoryDummyData {

	private static AssetCategoryDummyData instance = null;
	
	public static AssetCategoryDummyData getInstance() {
		if (instance == null) {
			instance = new AssetCategoryDummyData();
		}
		return instance;
	}
	
	public AssetCategoryDTO getDummyDTOAssetCategoryOne() {
		
		AssetCategoryDTO assetCategoryDTO = new AssetCategoryDTO();
		assetCategoryDTO.setName("Test Asset Category 1");
		assetCategoryDTO.setDescription("Description 1");		
		assetCategoryDTO.setOverideRule("overideRule 1");		
		assetCategoryDTO.setSysCode(1);		
		assetCategoryDTO.setParentId(1);	
	//	assetCategoryDTO.setType(type);
		
				
		return assetCategoryDTO;

}
	
	public AssetCategoryDTO getDummyDTOAssetCategoryTwo() {
		
		AssetCategoryDTO assetCategoryDTO = new AssetCategoryDTO();
		assetCategoryDTO.setName("Test Asset Category 2");
		assetCategoryDTO.setDescription("Description 2");		
		assetCategoryDTO.setOverideRule("overideRule 2");		
		assetCategoryDTO.setSysCode(1);		
		assetCategoryDTO.setParentId(1);	
	//	assetCategoryDTO.setType(type);
		
				
		return assetCategoryDTO;
}
	
	public List<AssetCategoryDTO> getAllDummyData(){
		List<AssetCategoryDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOAssetCategoryOne());
		list.add(getDummyDTOAssetCategoryTwo());
			
		return list;
		

	}
}
