package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class UserGroupSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static UserGroupSearchPropertyMapper instance = null;

	private UserGroupSearchPropertyMapper() {
	}

	public static UserGroupSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new UserGroupSearchPropertyMapper();
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
