package com.codex.ecam.util.search.asset;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AssetModelSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static AssetModelSearchPropertyMapper instance = null;
	
	private AssetModelSearchPropertyMapper() {}
	
	public static AssetModelSearchPropertyMapper getInstance(){
		if (instance == null) {
			instance = new AssetModelSearchPropertyMapper();
		} 
		return instance;
	}
	
	@Override
	protected void mapSearchParamsToPropertyParams(String column) {
		
		switch (column) {
		case "brandName":
			addColumns("assetBrand.brandName");
			break;

		default:
			break;
		}

	}

}
