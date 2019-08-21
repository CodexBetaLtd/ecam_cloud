package com.neolith.focus.util.search.workorder;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class WorkOrderSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static WorkOrderSearchPropertyMapper instance = null;

	private WorkOrderSearchPropertyMapper(){}

	public static WorkOrderSearchPropertyMapper getInstance (){
		if(instance == null){
			instance = new WorkOrderSearchPropertyMapper();
		}

		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String column) {

		switch (column) {

		case "assetNameStr":
			addColumns("workOrderAssets.asset.name");
			break;

		case "requestedByUserName":
			addColumns("requestedByUser.fullName");
			break;
			
		case "businessName":
			addColumns("business.name");
			break;

		default:
			break;
		}
	}

}
