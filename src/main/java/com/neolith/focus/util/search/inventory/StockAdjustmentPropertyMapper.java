package com.neolith.focus.util.search.inventory;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class StockAdjustmentPropertyMapper extends BaseSearchPropertyMapper {


    private static StockAdjustmentPropertyMapper instance = null;

    private StockAdjustmentPropertyMapper() {
    }

    public static StockAdjustmentPropertyMapper getInstance() {
        if (instance == null) {
            instance = new StockAdjustmentPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "adjustmentCode":
                addColumns("adjustmentCode");
                break;

            case "partName":
                addColumns("part.name");
                break;

            case "lastQuantity":
                addColumns("lastQuantity");
                break;

            case "newQuantity":
                addColumns("newQuantity");
                break;


            default:
                break;

        }

    }
}
