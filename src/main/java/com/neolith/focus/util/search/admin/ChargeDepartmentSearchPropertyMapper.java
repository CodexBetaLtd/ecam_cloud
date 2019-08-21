package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class ChargeDepartmentSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static ChargeDepartmentSearchPropertyMapper instance = null;

	private ChargeDepartmentSearchPropertyMapper() {
	}

	public static ChargeDepartmentSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new ChargeDepartmentSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String tableColumn) {

		switch (tableColumn) { 
	
	        case "businessName" : 
	        	addColumns("business.name");
	        	break;
	        	
			default:
				break;

		}
	}

}
