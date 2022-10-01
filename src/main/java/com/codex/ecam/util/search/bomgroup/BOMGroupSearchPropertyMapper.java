package com.codex.ecam.util.search.bomgroup;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class BOMGroupSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static BOMGroupSearchPropertyMapper instance = null;
	
	private BOMGroupSearchPropertyMapper(){}
	
	public static BOMGroupSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new BOMGroupSearchPropertyMapper();
		}
		
		return instance;
	}
	
	@Override
	protected void mapSearchParamsToPropertyParams(String column) {

		switch (column) { 
			
		case "businessName":
			addColumns("business.name");
			break;

		default:
			break;
		}
	}

}
