package com.codex.ecam.util.search.admin;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class PrioritySearchPropertyMapper extends BaseSearchPropertyMapper {

	private static PrioritySearchPropertyMapper instance = null;

	private PrioritySearchPropertyMapper() {
	}

	public static PrioritySearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new PrioritySearchPropertyMapper();
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
