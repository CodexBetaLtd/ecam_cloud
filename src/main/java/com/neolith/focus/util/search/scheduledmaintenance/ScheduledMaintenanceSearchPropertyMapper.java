package com.neolith.focus.util.search.scheduledmaintenance;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class ScheduledMaintenanceSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static ScheduledMaintenanceSearchPropertyMapper instance = null;
	
	private ScheduledMaintenanceSearchPropertyMapper(){}
	
	public static ScheduledMaintenanceSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new ScheduledMaintenanceSearchPropertyMapper();
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
