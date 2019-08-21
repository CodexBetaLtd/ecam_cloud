package com.neolith.focus.util.search.taskgroup;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class TaskGroupSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static TaskGroupSearchPropertyMapper instance = null;
	
	private TaskGroupSearchPropertyMapper(){}
	
	public static TaskGroupSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new TaskGroupSearchPropertyMapper();
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
