package com.codex.ecam.util.search.admin;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class MaintenanceTypeSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static MaintenanceTypeSearchPropertyMapper instance = null;

    private MaintenanceTypeSearchPropertyMapper() {
    }

    public static MaintenanceTypeSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new MaintenanceTypeSearchPropertyMapper();
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
