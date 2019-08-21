package com.neolith.focus.util.search.inventory.stock;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class StockPartPropertyMapper extends BaseSearchPropertyMapper {


    private static StockPartPropertyMapper instance = null;

    private StockPartPropertyMapper() {
    }

    public static StockPartPropertyMapper getInstance() {
        if (instance == null) {
            instance = new StockPartPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "partCode":
                addColumns("part.code");
                break;
            case "code":
                addColumns("part.code");
                break;

            case "partNo":
                addColumns("part.name");
                break;

            case "partName":
                addColumns("part.name");
                break;

            case "assetBrandName":
                addColumns("part.brand.brandName");
                break;

            case "stockNo":
                addColumns("stockNo");
                break;

            case "description":
                addColumns("part.description");
                break;

            default:
                break;

        }

    }
}
