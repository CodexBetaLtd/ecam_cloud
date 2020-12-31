package com.codex.ecam.util.search.inventory.aod;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AODItemPropertyMapper extends BaseSearchPropertyMapper {


    private static AODItemPropertyMapper instance = null;

    private AODItemPropertyMapper() {
    }

    public static AODItemPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AODItemPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "warehouseName":
                addColumns("warehouse.name");
                break;

            case "partName":
                addColumns("part.name");
                break;

            case "itemQuantity":
                addColumns("quantity");
                break;
            case "itemReturnQuantity":
                addColumns("returnQuantity");
                break;
            default:
                break;

        }

    }
}
