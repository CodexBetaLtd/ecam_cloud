package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class AssetModelSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static AssetModelSearchPropertyMapper instance = null;

    private AssetModelSearchPropertyMapper() {
    }

    public static AssetModelSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AssetModelSearchPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String tableColumn) {

        switch (tableColumn) {

            default:
                break;

        }
    }

}
