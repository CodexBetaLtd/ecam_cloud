package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class AssetEventTypeSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static AssetEventTypeSearchPropertyMapper instance = null;

    private AssetEventTypeSearchPropertyMapper() {
    }

    public static AssetEventTypeSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AssetEventTypeSearchPropertyMapper();
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
