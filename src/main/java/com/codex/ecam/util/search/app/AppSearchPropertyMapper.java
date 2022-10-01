package com.codex.ecam.util.search.app;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AppSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static AppSearchPropertyMapper instance = null;

    private AppSearchPropertyMapper() {
    }

    public static AppSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AppSearchPropertyMapper();
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
