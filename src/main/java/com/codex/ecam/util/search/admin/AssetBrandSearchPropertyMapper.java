package com.codex.ecam.util.search.admin;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AssetBrandSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static AssetBrandSearchPropertyMapper instance = null;

    private AssetBrandSearchPropertyMapper() {
    }

    public static AssetBrandSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AssetBrandSearchPropertyMapper();
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
