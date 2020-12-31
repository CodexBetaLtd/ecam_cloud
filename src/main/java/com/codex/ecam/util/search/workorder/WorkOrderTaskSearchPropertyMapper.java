package com.codex.ecam.util.search.workorder;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class WorkOrderTaskSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static WorkOrderTaskSearchPropertyMapper instance = null;
	
	private WorkOrderTaskSearchPropertyMapper(){}
	
	public static WorkOrderTaskSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new WorkOrderTaskSearchPropertyMapper();
		}
		
		return instance;
	}
	
	@Override
	protected void mapSearchParamsToPropertyParams(String column) {

		switch (column) {
		
		case "assignedUserName":
			addColumns("assignedUser.fullName");
			break; 

		default:
			break;
		}
	}

}
