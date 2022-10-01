package com.codex.ecam.util.search.admin;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class UserSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static UserSearchPropertyMapper instance = null;

	private UserSearchPropertyMapper() {
	}

	public static UserSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new UserSearchPropertyMapper();
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
