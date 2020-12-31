package com.codex.ecam.util.search.scheduledmaintenance;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class ScheduledMaintenanceTaskSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static ScheduledMaintenanceTaskSearchPropertyMapper instance = null;
	
	private ScheduledMaintenanceTaskSearchPropertyMapper(){}
	
	public static ScheduledMaintenanceTaskSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new ScheduledMaintenanceTaskSearchPropertyMapper();
		}
		
		return instance;
	}
	
	@Override
	protected void mapSearchParamsToPropertyParams(String column) {

		switch (column) {
		
		case "taskType":
			addColumns("taskType");
			break;
			
		case "userName":
			addColumns("assignedUser.fullName");
			break;

		default:
			break;
		}
	}

}
