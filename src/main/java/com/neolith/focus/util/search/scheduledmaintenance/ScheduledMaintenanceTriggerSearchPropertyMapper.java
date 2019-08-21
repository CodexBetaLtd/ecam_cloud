package com.neolith.focus.util.search.scheduledmaintenance;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class ScheduledMaintenanceTriggerSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static ScheduledMaintenanceTriggerSearchPropertyMapper instance = null;
	
	private ScheduledMaintenanceTriggerSearchPropertyMapper(){}
	
	public static ScheduledMaintenanceTriggerSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new ScheduledMaintenanceTriggerSearchPropertyMapper();
		}
		
		return instance;
	}
	
	@Override
	protected void mapSearchParamsToPropertyParams(String column) {

		switch (column) {
		
		case "triggerType":
			addColumns("triggerType");
			break;
			
		case "assetName":
			addColumns("asset.name");
			break;
			
		case "scheduledDate":
			addColumns("ttNextCalenderEvent.scheduledDate");
			break;

		default:
			break;
		}
	}

}
