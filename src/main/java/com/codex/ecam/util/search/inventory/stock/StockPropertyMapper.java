package com.codex.ecam.util.search.inventory.stock;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class StockPropertyMapper extends BaseSearchPropertyMapper {


    private static StockPropertyMapper instance = null;

    private StockPropertyMapper() {
    }

    public static StockPropertyMapper getInstance() {
        if (instance == null) {
            instance = new StockPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "partName":
                addColumns("part.name");
                break;
                
            case "partCode":
            	addColumns("part.code");
            	break;

            case "wharehouseName":
                addColumns("warehouse.name");
                break;

            default:
                break;

        }

    }
}
