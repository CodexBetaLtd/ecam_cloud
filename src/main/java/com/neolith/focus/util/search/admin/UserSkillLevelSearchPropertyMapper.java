package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class UserSkillLevelSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static UserSkillLevelSearchPropertyMapper instance = null;

	private UserSkillLevelSearchPropertyMapper() {
	}

	public static UserSkillLevelSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new UserSkillLevelSearchPropertyMapper();
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
