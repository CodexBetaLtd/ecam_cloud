package com.codex.ecam.util.search.asset;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AssetCategoryPropertyMapper extends BaseSearchPropertyMapper {
	
	
	private static AssetCategoryPropertyMapper instance = null;
	
	private AssetCategoryPropertyMapper() {}
	
	public static AssetCategoryPropertyMapper getInstance() {
		if(instance == null){
			instance = new AssetCategoryPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String column) {
		
		switch (column) {
		
		case "parentName":
			addColumns("parentAssetCategory.name");
			break;

		default:
			break;
			
		}

	}
}
