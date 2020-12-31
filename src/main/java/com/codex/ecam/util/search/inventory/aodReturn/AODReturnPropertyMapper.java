package com.codex.ecam.util.search.inventory.aodReturn;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AODReturnPropertyMapper extends BaseSearchPropertyMapper {


    private static AODReturnPropertyMapper instance = null;

    private AODReturnPropertyMapper() {
    }

    public static AODReturnPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AODReturnPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "returnNo":
                addColumns("returnNo");
                break;

            case "returnRefNo":
                addColumns("returnRefNo");
                break;

            case "returnDate":
                addColumns("returnDate");
                break;

            default:
                break;

        }

    }
}
