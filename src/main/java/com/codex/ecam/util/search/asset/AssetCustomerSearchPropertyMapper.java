package com.codex.ecam.util.search.asset;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AssetCustomerSearchPropertyMapper extends BaseSearchPropertyMapper {	
	
	private static AssetCustomerSearchPropertyMapper instance = null;
	
	private AssetCustomerSearchPropertyMapper() {}
	
	public static AssetCustomerSearchPropertyMapper getInstance() {
		if(instance == null){
			instance = new AssetCustomerSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String column) {
		
		switch (column) {
		
		case "customerName":
			addColumns("customer.name");
			break;
			
		case "address":
			addColumns("customer.address");
			break;
			
		case "date":
			addColumns("createdDate");
			break;

		default:
			break;
			
		}

	}

}
