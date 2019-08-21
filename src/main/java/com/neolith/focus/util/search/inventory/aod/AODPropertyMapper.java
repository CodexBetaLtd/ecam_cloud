package com.neolith.focus.util.search.inventory.aod;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class AODPropertyMapper extends BaseSearchPropertyMapper {


    private static AODPropertyMapper instance = null;

    private AODPropertyMapper() {
    }

    public static AODPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AODPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "aodNo":
                addColumns("aodNo");
                break;
                
            case "date":
            	addColumns("date");
            	break;

            case "woNo":
                addColumns("workOrder.code");
                break;

            default:
                break;

        }

    }
}
