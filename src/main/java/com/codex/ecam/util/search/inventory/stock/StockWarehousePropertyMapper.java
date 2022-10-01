package com.codex.ecam.util.search.inventory.stock;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class StockWarehousePropertyMapper extends BaseSearchPropertyMapper {


    private static StockWarehousePropertyMapper instance = null;

    private StockWarehousePropertyMapper() {
    }

    public static StockWarehousePropertyMapper getInstance() {
        if (instance == null) {
            instance = new StockWarehousePropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "partCode":
                addColumns("part.code");
                break;

            case "partName":
                addColumns("part.name");
                break;

            case "stockNo":
                addColumns("stockNo");
                break;

            case "qtyOnHand":
                addColumns("currentQuantity");
                break;

            default:
                break;

        }

    }
}
