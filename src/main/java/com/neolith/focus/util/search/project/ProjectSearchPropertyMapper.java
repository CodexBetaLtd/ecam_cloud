package com.neolith.focus.util.search.project;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class ProjectSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static ProjectSearchPropertyMapper instance = null;
	
	private ProjectSearchPropertyMapper(){}
	
	public static ProjectSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new ProjectSearchPropertyMapper();
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
