package com.codex.ecam.util.search.stockage;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class StockAgePropertyMapper extends BaseSearchPropertyMapper {


    private static StockAgePropertyMapper instance = null;

    private StockAgePropertyMapper() {
    }

    public static StockAgePropertyMapper getInstance() {
        if (instance == null) {
            instance = new StockAgePropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "stockAgeStockNo":
                addColumns("part.code");
                break;
            case "stockAgePartCode":
                addColumns("part.code");
                break;

            case "stockAgePartDescription":
                addColumns("part.name");
                break;

            default:
                break;

        }

    }
}
