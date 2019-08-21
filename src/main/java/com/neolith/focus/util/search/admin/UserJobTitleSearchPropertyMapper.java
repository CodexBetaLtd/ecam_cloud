package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class UserJobTitleSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static UserJobTitleSearchPropertyMapper instance = null;

	private UserJobTitleSearchPropertyMapper() {
	}

	public static UserJobTitleSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new UserJobTitleSearchPropertyMapper();
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
