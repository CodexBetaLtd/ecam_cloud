package com.neolith.focus.util.search.inventory;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class PartSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static PartSearchPropertyMapper instance = null;

	private PartSearchPropertyMapper() {
	}

	public static PartSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new PartSearchPropertyMapper();
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
